package frc.robot.commands;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap.Gyro;
import frc.robot.RobotMap.SoftwareObjects;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;

public class ShooterCommand extends Command {
    private final ShooterSubsys shooter;
    private final SwerveSubsys swerve;
    private final PIDController seekingPID = new PIDController(0.2, 0, 0.001);
    private final Pigeon2 gyro = Gyro.GYRO;
    private final SwerveDrivePoseEstimator3d poseEst = SoftwareObjects.poseEstimator;

    private final double hubXPosition = Meters.convertFrom(651.22 - 182.11, Inches); //in meters
    private final double hubYPosition = 4.034536; //also in meters
    private double destinationAngle = 0;
    private Pose2d currentPos;

    private enum States {
        SEEKING, SHOOTING
    };

    private States currentState = States.SEEKING;


    public ShooterCommand(ShooterSubsys shooter, SwerveSubsys swerve) {
        super();
        super.addRequirements(shooter);
        this.shooter = shooter;
        this.swerve = swerve;
    }

    @Override
    public void initialize() {
        swerve.setCanMove(false);
        swerve.setCanRotate(false);
        // shooter.shooterOn();
    }

    @Override
    public void execute() {
        switch (currentState) {
            case SEEKING:
                currentPos = poseEst.getEstimatedPosition().toPose2d();
                double correctedX = hubXPosition - currentPos.getX();
                double correctedY = hubYPosition - currentPos.getY();
                destinationAngle = (correctedY > 0 ? (Math.PI / 2) : (-Math.PI / 2)) - Math.atan(hubXPosition - currentPos.getX() / hubYPosition - currentPos.getY());
                swerve.drive(0, 0, seekingPID.calculate(gyro.getRotation2d().getRadians() + (Math.PI / 2), destinationAngle), true);
                if (seekingPID.atSetpoint()) {
                    currentState = States.SHOOTING;
                }
                break;
            case SHOOTING:
                System.out.println("HOLY CRAP IT'S IN DEADZONE???");
                break;
        }

    }

    @Override
    public void end(boolean interrupted) {
        swerve.setCanMove(true);
        swerve.setCanRotate(true);
    }
}
