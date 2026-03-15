package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.DoubleArrayEntry;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.struct.Struct;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.Gyro;
import frc.robot.subsystems.swerve.MAXSwerveModule;
import frc.robot.subsystems.swerve.SwerveConstants.DriveConstants;

public class SwerveSubsys extends SubsystemBase {
    // This is directly copied from MAXSwerve template

    // private GameController controller = Controller.GAME_CONTROLLER;
    // Variables used in the run lambda:
    private double x;
    private double y;
    private double rot;
    private final CommandXboxController controller = Controller.XBOX;

    // Create MAXSwerveModules
    private final MAXSwerveModule m_frontLeft = RobotMap.SoftwareObjects.FRONT_LEFT_MODULE;
    private final MAXSwerveModule m_frontRight = RobotMap.SoftwareObjects.FRONT_RIGHT_MODULE;
    private final MAXSwerveModule m_rearLeft = RobotMap.SoftwareObjects.BACK_LEFT_MODULE;
    private final MAXSwerveModule m_rearRight = RobotMap.SoftwareObjects.BACK_RIGHT_MODULE;
    private final NetworkTableInstance network = RobotMap.SoftwareObjects.networkTableInstance;

    private DoubleEntry positionXEntry = network.getDoubleTopic("PositionX").getEntry(0);
    private DoubleEntry positionYEntry = network.getDoubleTopic("PositionY").getEntry(0);
    private DoubleEntry positionZEntry = network.getDoubleTopic("PositionZ").getEntry(0);

    private Field2d field2d = new Field2d();
    private GenericEntry field2dEntry = network.getTopic("Field2d").getGenericEntry();

    private Pigeon2 gyro = Gyro.GYRO;
    private SwerveDrivePoseEstimator3d odometry = RobotMap.SoftwareObjects.poseEstimator;

    public SwerveSubsys() {
        super();
        // The MaxSwerve template does this, no clue what this is
        HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);

        positionXEntry.set(0.0);
        positionYEntry.set(0.0);
        positionZEntry.set(0.0);

        field2d.setRobotPose(0, 0, new Rotation2d());
        // field2dEntry.set(field2d);

        // network.struct
    }

    // Mostly copied from MaxSwerve template, simply updates
    // the odometry every cycle
    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        odometry.update(gyro.getRotation3d(), new SwerveModulePosition[] {m_frontLeft.getPosition(), m_frontRight.getPosition(), m_rearLeft.getPosition(), m_rearRight.getPosition()});

        Pose3d pos = odometry.getEstimatedPosition();
        positionXEntry.set(pos.getX());
        positionYEntry.set(pos.getY());
        positionZEntry.set(pos.getZ());

        field2d.setRobotPose(pos.toPose2d());
    }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed        Speed of the robot in the x direction (forward).
     * @param ySpeed        Speed of the robot in the y direction (sideways).
     * @param rot           Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the
     *                      field.
     */
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        // Convert the commanded speeds into the correct units for the drivetrain
        double xSpeedDelivered = xSpeed * DriveConstants.MAX_SPEED_METERS_PER_SECOND;
        double ySpeedDelivered = ySpeed * DriveConstants.MAX_SPEED_METERS_PER_SECOND;
        double rotDelivered = rot * DriveConstants.MAX_ANGULAR_SPEED;

        SwerveModuleState[] swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered, gyro.getRotation2d()) : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.MAX_SPEED_METERS_PER_SECOND);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_rearLeft.setDesiredState(swerveModuleStates[2]);
        m_rearRight.setDesiredState(swerveModuleStates[3]);
    }

    /**
     * Run periodically during teleop
     * 
     * @return
     */

    public Command teleopDrive() {
        return run(() -> {
            // Done this way in order to easily enforce controller deadzones since this
            // isn't already done in drive()

            //TODO: Lock direction to straight forward, left, right, and back when stick is in the extreme of that direction.
            //Videogames do this very often and I figure for the same reason videogames do it, we should too.
            x = controller.getLeftX();
            x = Math.abs(x) > RobotMap.DigitalValues.CONTROLLER_DEADZONE ? x : 0.0;

            y = controller.getLeftY();
            y = Math.abs(y) > RobotMap.DigitalValues.CONTROLLER_DEADZONE ? y : 0.0; // Both X and Y are reversed in order to make the shooter the front of the robot

            if (Math.abs(x) >= 0.99 && Math.abs(y) <= 0.2) {
                x = 1 * Math.signum(x);
                y = 0;
            } else if (Math.abs(y) >= 0.99 && Math.abs(x) <= 0.2) {
                x = 0;
                y = 1 * Math.signum(y);
            }

            // System.out.println(x + " | " + y);

            rot = controller.getRightX();
            rot = Math.abs(rot) > RobotMap.DigitalValues.CONTROLLER_DEADZONE ? rot * 0.85 : 0.0; //rot * -0.85 to reverse direction of rotation and slow it down since it was overly responsive

            // TODO: Set this back to true when robot is in better shape, false to be easier
            // to work with for now.
            // Realistically, it needs to be possible to make it not field relative, maybe a
            // hold or something.
            drive(x, y, rot, false);
        });
    }
}
