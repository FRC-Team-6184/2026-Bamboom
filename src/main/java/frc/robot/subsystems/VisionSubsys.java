package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Inches;
import java.util.List;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N4;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Gyro;

public class VisionSubsys extends SubsystemBase {
    // Define the cameras
    private PhotonCamera limeLight = new PhotonCamera("Limelight");
    // private PhotonCamera leftCam = new PhotonCamera("LeftCameraReal");
    // private PhotonCamera rightCam = new PhotonCamera("RightCameraReal");
    // Each camera needs its own pose estimator, these will end up talking to the pose estimator for drive
    private Transform3d limeLightTransform = new Transform3d(Inches.of(12.5), Inches.of(0), Inches.of(20.5), new Rotation3d(0, 0, 0)); //8.5 + limelight thickness (1.22) x, 20.5 z, 2 y all in inches
    private Transform3d rightTransform = new Transform3d(Inches.of(6.0), Inches.of(-12.5), Inches.of(21.25), new Rotation3d(0, 0, 0));
    private Transform3d leftTransform = new Transform3d(Inches.of(6.0), Inches.of(12.5), Inches.of(21.25), new Rotation3d(0, 0, 0));

    private PhotonPoseEstimator limeLightEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), limeLightTransform);
    private PhotonPoseEstimator rightEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), rightTransform);
    private PhotonPoseEstimator leftEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), leftTransform);

    public Pose2d lastEstimate;

    private boolean wasUpdated = false;

    //Global drive pose estimation
    private SwerveDrivePoseEstimator3d globalEstimator = RobotMap.SoftwareObjects.poseEstimator;

    public VisionSubsys() {
        super();

        limeLight.setDriverMode(true);
    }

    int count = 0;

    @Override
    public void periodic() {
        applyEstimations(limeLight.getAllUnreadResults());
        // applyEstimations(leftCam.getAllUnreadResults());
        // applyEstimations(rightCam.getAllUnreadResults());

        // count++;
        // if (count >= 100) {
        // System.out.println("Vision stuff is in fact actually running");
        // }

        // rightEstimator.
    }

    private void applyEstimations(List<PhotonPipelineResult> unreadResults) {
        if (!unreadResults.isEmpty()) {
            for (PhotonPipelineResult result : limeLight.getAllUnreadResults()) {
                Optional<EstimatedRobotPose> poseHolder = limeLightEstimator.estimateCoprocMultiTagPose(result);
                if (poseHolder.isEmpty()) {
                    limeLightEstimator.estimateLowestAmbiguityPose(result);
                }
                //If it's still empty somehow after this then it's simply not meant to be
                if (poseHolder.isPresent()) {
                    dynamicStandardDeviation(result);
                    EstimatedRobotPose pose = poseHolder.get();
                    lastEstimate = pose.estimatedPose.toPose2d();
                    globalEstimator.addVisionMeasurement(pose.estimatedPose, pose.timestampSeconds);
                    // globalEstimator.resetPose(pose.estimatedPose);
                    // Gyro.GYRO.setYaw(pose.estimatedPose.getRotation().toRotation2d().getDegrees());
                    // wasUpdated = true;
                }
            }
        }
    }

    private void dynamicStandardDeviation(PhotonPipelineResult result) {
        PhotonTrackedTarget target = result.getBestTarget();

        if (!(target == null)) {
            Transform3d camToTarget = target.getBestCameraToTarget();
            double distance = camToTarget.getTranslation().getDistance(new Translation3d(0, 0, 0));

            //Basic placeholder equation for dynamic stddev based on distance, 0.16x^2, theta always being at 0
            double calcValue = distance * 0.75;
            Matrix<N4, N1> stddevs = new Matrix<N4, N1>(Nat.N4(), Nat.N1());
            stddevs.set(0, 0, calcValue);
            stddevs.set(1, 0, calcValue);
            stddevs.set(2, 0, calcValue);
            stddevs.set(3, 0, 0);
            globalEstimator.setVisionMeasurementStdDevs(stddevs);
        } else {
            return;
        }
    }

    public boolean getWasUpdated() {
        if (wasUpdated) {
            wasUpdated = false;
            return true;
        }

        return false;
    }

}
