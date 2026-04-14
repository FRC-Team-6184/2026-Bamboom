package frc.robot.utilities;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation3d;

public class DumbGyroWrapper {
    Pigeon2 gyro;

    public DumbGyroWrapper(Pigeon2 gyro) {
        this.gyro = gyro;
    }

    public Rotation3d getRotation3d() {
        return gyro.getRotation3d().rotateBy(new Rotation3d(0.0, 0.0, -90.0));
    }

}
