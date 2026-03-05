package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.GameController;
import frc.robot.hardware.HardwareManager.Controller;
import frc.robot.hardware.HardwareManager.Gyro;
import frc.robot.hardware.HardwareManager.MotorControllers;
import frc.robot.swerve.MAXSwerveModule;
import frc.robot.swerve.SwerveConstants.DriveConstants;
import frc.robot.utilities.MathUtil;

public class SwerveDrive extends SubsystemBase {
    // This is directly copied from MAXSwerve template

    // Create MAXSwerveModules
    private final MAXSwerveModule m_frontLeft = new MAXSwerveModule(
        MotorControllers.FR_DRIVE_MOTOR,
        MotorControllers.FL_TURN_MOTOR,
        DriveConstants.FRONT_LEFT_CHASSIS_ANGULAR_OFFSET);

    private final MAXSwerveModule m_frontRight = new MAXSwerveModule(
        MotorControllers.FR_DRIVE_MOTOR,
        MotorControllers.FR_TURN_MOTOR,
        DriveConstants.FRONT_RIGHT_CHASSIS_ANGULAR_OFFSET);

    private final MAXSwerveModule m_rearLeft = new MAXSwerveModule(
        MotorControllers.BL_DRIVE_MOTOR,
        MotorControllers.BL_TURN_MOTOR,
        DriveConstants.BACK_LEFT_CHASSIS_ANGULAR_OFFSET);

    private final MAXSwerveModule m_rearRight = new MAXSwerveModule(
        MotorControllers.BR_DRIVE_MOTOR,
        MotorControllers.BR_TURN_MOTOR,
        DriveConstants.BACK_RIGHT_CHASSIS_ANGULAR_OFFSET);

    private Pigeon2 gyro = Gyro.GYRO;

    private GameController controller = Controller.GAME_CONTROLLER;

    private SwerveDriveOdometry odometry = new SwerveDriveOdometry(
            DriveConstants.kDriveKinematics,
            gyro.getRotation2d(),
            new SwerveModulePosition[] {
                    m_frontLeft.getPosition(),
                    m_frontRight.getPosition(),
                    m_rearLeft.getPosition(),
                    m_rearRight.getPosition()
            });

    public SwerveDrive() {
        super();
        // The MaxSwerve template does this, no clue what this is
        HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);
    }

    // Mostly copied from MaxSwerve template, simply updates
    // the odometry every cycle
    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        odometry.update(
                gyro.getRotation2d(),
                new SwerveModulePosition[] {
                        m_frontLeft.getPosition(),
                        m_frontRight.getPosition(),
                        m_rearLeft.getPosition(),
                        m_rearRight.getPosition()
                });
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

        SwerveModuleState[] swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
                fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered,
                                ySpeedDelivered, rotDelivered,
                                gyro.getRotation2d())
                        : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        SwerveDriveKinematics.desaturateWheelSpeeds(
                swerveModuleStates, DriveConstants.MAX_SPEED_METERS_PER_SECOND);
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
            // Done this way in order to easily enforce controller deadzones since this isn't already done in drive()
            double x = controller.getLeftX();
            x = Math.abs(x) > Hardware.CONTROLLER_DEADZONE ? x : 0.0;
            double y = controller.getLeftY();
            y = Math.abs(y) > Hardware.CONTROLLER_DEADZONE ? y : 0.0;
            double rot = controller.getRightX();
            rot = Math.abs(rot) > Hardware.CONTROLLER_DEADZONE ? rot : 0.0;
            drive(x, y, rot, false);
            // TODO: Set this back to true when robot is in better shape, false to be easier to work with for now.
            // Realistically, it needs to be possible to make it not field relative, maybe a hold or something.
        });
    }
}