package frc.robot.subsystems;

import org.photonvision.PhotonPoseEstimator;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.Controller;

public class Vision extends SubsystemBase {
    PhotonPoseEstimator test = new PhotonPoseEstimator(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded), null);
    // Photon




    public Vision() {
        super();
    }


    
}
