package frc.robot.commands.swerve;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.Gyro;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.swerve.MAXSwerveModule;
import frc.robot.subsystems.swerve.SwerveConstants.DriveConstants;

public class SwerveTeleopDriveCommand extends Command {
    // Related subsytem and controller
    SwerveSubsys swerve;
    private final CommandXboxController controller = Controller.XBOX;

    // Execute command stuff
    private boolean canMove = true;
    private boolean canRotate = true;
    private double rot;
    private double x;
    private double y;

    private final Pigeon2 gyro = Gyro.GYRO;

    // Swerve modules
    private final MAXSwerveModule m_frontLeft = RobotMap.SoftwareObjects.FRONT_LEFT_MODULE;
    private final MAXSwerveModule m_frontRight = RobotMap.SoftwareObjects.FRONT_RIGHT_MODULE;
    private final MAXSwerveModule m_rearLeft = RobotMap.SoftwareObjects.BACK_LEFT_MODULE;
    private final MAXSwerveModule m_rearRight = RobotMap.SoftwareObjects.BACK_RIGHT_MODULE;

    private SwerveDriveKinematics kinematics = DriveConstants.kDriveKinematics;
    private int counter = 0;

    public SwerveTeleopDriveCommand(SwerveSubsys swerve) {
        this.swerve = swerve;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (canMove) {
            // Done this way in order to easily enforce controller deadzones since this
            // isn't already done in drive()
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
        } else {
            x = 0;
            y = 0;
        }

        // System.out.println(x + " | " + y);
        if (canRotate) {
            rot = -controller.getRightX();
            rot = Math.abs(rot) > RobotMap.DigitalValues.CONTROLLER_DEADZONE ? rot * 0.85 : 0.0; //rot * -0.85 to reverse direction of rotation and slow it down since it was overly responsive
        } else {
            rot = 0;
        }
        // TODO: Set this back to true when robot is in better shape, false to be easier
        // to work with for now.
        // Realistically, it needs to be possible to make it not field relative, maybe a
        // hold or something.
        drive(-x, y, rot, true);
    }

    @Override
    public void end(boolean interrupted) {}

    private void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        // Convert the commanded speeds into the correct units for the drivetrain
        double xSpeedDelivered = xSpeed * DriveConstants.MAX_SPEED_METERS_PER_SECOND;
        double ySpeedDelivered = ySpeed * DriveConstants.MAX_SPEED_METERS_PER_SECOND;
        double rotDelivered = rot * DriveConstants.MAX_ANGULAR_SPEED;

        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered, gyro.getRotation2d()) : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.MAX_SPEED_METERS_PER_SECOND);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_rearLeft.setDesiredState(swerveModuleStates[2]);
        m_rearRight.setDesiredState(swerveModuleStates[3]);

        counter++;
        if (counter >= 10) {
            System.out.println(swerveModuleStates[0].angle.getDegrees());
            counter = 0;
        }
    }
}
