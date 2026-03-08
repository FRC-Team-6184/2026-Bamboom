package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.RobotMap.Controller;

public class Vision extends SubsystemBase {
    // Define the cameras
    PhotonCamera limeLight = new PhotonCamera("Photon-Limelight");
    PhotonCamera leftCam;
    PhotonCamera rightCam;
    // Each camera needs its own pose estimator, these will end up talking to the pose estimator for drive
    PhotonPoseEstimator limeLightEstimator = new PhotonPoseEstimator(
            AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), null);
    PhotonPoseEstimator rightEstimator = new PhotonPoseEstimator(
            AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), null);
    PhotonPoseEstimator leftEstimator = new PhotonPoseEstimator(
            AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), null);

    public Vision() {
        super();
        limeLight.setDriverMode(true);
    }

    public void test() {

    }

}
