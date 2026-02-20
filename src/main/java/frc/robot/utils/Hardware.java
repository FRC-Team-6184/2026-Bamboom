package frc.robot.utils;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

//Made the class final as everything here should be pretty much constant, but if it 
//becomes an issue later just remove it.

/** CAN IDs of Hardware:
 * Front Left Swerve Drive - 1
 * Front Left Swerve Turn - 2
 * Front Right Swerve Drive - 3
 * Front Right Swerve Turn - 4
 * Back Left Swerve Drive - 5
 * Back Left Swerve Turn - 6
 * Back Right Swerve Drive - 7
 * Back Right Swerve Turn - 8
 * 
 * Top Wheel of Shooter - 9
 * Bottom Wheel of Shooter - 10
 * 
 * Gyro (Pigeon2) - 20
 * PDH - 21
 * 
 */

//Motors for Swerve Drive aren't found here as they are managed by the Swerve Drive Modules in the SwerveDrive subsystem
public final class Hardware {
    //TODO: Pretty sure this CAN ID is shared by something else, possibly the PDH. Resolve the conflict!
    public static final Pigeon2 gyro = new Pigeon2(20); 
    public static final GameController controller = new GameController(0);

    public static final TalonFX bottomShooterMotor = new TalonFX(10);
    public static final TalonFX topShooterMotor = new TalonFX(9);

    // public static final TalonFX blenderMotor = new TalonFX(-1);

    //Not hardware related but this is the best class to put this in
    public static final NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
    public static final double CONTROLLER_DEADZONE = 0.12;


}
