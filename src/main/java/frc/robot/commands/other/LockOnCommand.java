package frc.robot.commands.other;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Radians;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap.Gyro;
import frc.robot.RobotMap.SoftwareObjects;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.VisionSubsys;

public class LockOnCommand extends Command {

    private SwerveSubsys swerve;

    private final double hubXPosition = Meters.convertFrom(651.22 - 182.11, Inches); //in meters
    private final double hubYPosition = 4.034536; //also in meters
    private double destinationAngle = 0;
    private Pose2d currentPos;
    private final PIDController seekingPID = new PIDController(0.5, 0.0125, 0);
    private final Pigeon2 gyro = Gyro.GYRO;
    private final SwerveDrivePoseEstimator3d poseEst = SoftwareObjects.poseEstimator;

    private VisionSubsys vision;


    public LockOnCommand(SwerveSubsys swerve, VisionSubsys vision) {
        this.swerve = swerve;
        this.vision = vision;
    }

    @Override
    public void initialize() {
        swerve.setCanRotate(false);
    }

    @Override
    public void execute() {
        boolean wasUpdated = vision.getWasUpdated();
        if (wasUpdated) {
            currentPos = vision.lastEstimate;
        } else {
            currentPos = poseEst.getEstimatedPosition().toPose2d();
        }
        // currentPos = poseEst.getEstimatedPosition().toPose2d();
        // currentPos = vision.lastEstimate;
        double correctedX = hubXPosition - currentPos.getX();
        double correctedY = hubYPosition - currentPos.getY();
        destinationAngle = (correctedY > 0 ? (Math.PI / 2) : (-Math.PI / 2)) - Math.atan(hubXPosition - currentPos.getX() / hubYPosition - currentPos.getY());
        swerve.setDesiredRot(seekingPID.calculate(getAdjustedGyroFromEstimator(), destinationAngle));
    }

    @Override
    public void end(boolean interrupted) {
        swerve.setCanRotate(true);
    }

    private double getAdjustedGyroFromVision() {
        double angle = gyro.getRotation2d().getDegrees() + 90;
        if (angle > 180.0) {
            angle -= 360;
        }

        return Radians.convertFrom(angle, Degrees);
    }

    private double getAdjustedGyroFromEstimator() {
        return gyro.getRotation2d().getRadians();
    }



}
