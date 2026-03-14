package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Inches;
import java.util.List;
import java.util.Optional;
import javax.xml.crypto.dsig.Transform;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Vision extends SubsystemBase {
    // Define the cameras
    PhotonCamera limeLight = new PhotonCamera("Limelight");
    PhotonCamera leftCam = new PhotonCamera("LeftCam");
    PhotonCamera rightCam = new PhotonCamera("RightCam");
    // Each camera needs its own pose estimator, these will end up talking to the pose estimator for drive
    Transform3d limeLightTransform = new Transform3d(Inches.of(9.72), Inches.of(2), Inches.of(20.5), new Rotation3d(0, 0, 0)); //8.5 + limelight thickness (1.22) x, 20.5 z, 2 y all in inches
    Transform3d rightTransform = new Transform3d(Inches.of(6.0), Inches.of(-12.5), Inches.of(21.25), new Rotation3d(0, 0, 0));
    Transform3d leftTransform = new Transform3d(Inches.of(6.0), Inches.of(12.5), Inches.of(21.25), new Rotation3d(0, 0, 0));

    PhotonPoseEstimator limeLightEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), limeLightTransform);
    PhotonPoseEstimator rightEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), rightTransform);
    PhotonPoseEstimator leftEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), leftTransform);

    //Global drive pose estimation
    SwerveDrivePoseEstimator3d globalEstimator = RobotMap.SoftwareObjects.poseEstimator;

    public Vision() {
        super();

        limeLight.setDriverMode(true);
    }

    @Override
    public void periodic() {
        applyEstimations(limeLight.getAllUnreadResults());
        applyEstimations(leftCam.getAllUnreadResults());
        applyEstimations(rightCam.getAllUnreadResults());
    }

    private void applyEstimations(List<PhotonPipelineResult> unreadResults) {
        for (PhotonPipelineResult result : limeLight.getAllUnreadResults()) {
            Optional<EstimatedRobotPose> poseHolder = limeLightEstimator.estimateCoprocMultiTagPose(result);
            if (poseHolder.isEmpty()) {
                limeLightEstimator.estimateLowestAmbiguityPose(result);
            }
            //If it's still empty somehow after this then it's simply not meant to be
            if (poseHolder.isPresent()) {
                EstimatedRobotPose pose = poseHolder.get();

                globalEstimator.addVisionMeasurement(pose.estimatedPose, pose.timestampSeconds);
            }
        }
    }

}
