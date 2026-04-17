package frc.robot.utilities;

import static edu.wpi.first.units.Units.Degrees;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation3d;

public class DumbGyroWrapper {
    Pigeon2 gyro;

    public DumbGyroWrapper(Pigeon2 gyro) {
        this.gyro = gyro;
    }

    public Rotation3d getRotation3d() {
        return gyro.getRotation3d().rotateBy(new Rotation3d(Degrees.of(0), Degrees.of(0), Degrees.of(180)));
    }

}
