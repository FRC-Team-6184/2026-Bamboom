package frc.robot.subsystems;

import java.util.List;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Vision extends SubsystemBase {
    // Define the cameras
    PhotonCamera limeLight = new PhotonCamera("Limelight");
    PhotonCamera leftCam = new PhotonCamera("LeftCam");
    PhotonCamera rightCam = new PhotonCamera("RightCam");
    // Each camera needs its own pose estimator, these will end up talking to the pose estimator for drive
    PhotonPoseEstimator limeLightEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), null);
    PhotonPoseEstimator rightEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), null);
    PhotonPoseEstimator leftEstimator = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), null);

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
