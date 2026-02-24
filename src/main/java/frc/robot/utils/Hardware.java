package frc.robot.utils;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.NetworkTableInstance;

//Made the class final as everything here should be pretty much constant, but if it 
//becomes an issue later just remove it.

/** CAN IDs of Hardware:
 * Front Left Swerve Drive - 1 | Neo (Rev)
 * Front Left Swerve Turn - 2 | Neo 550 (Rev)
 * Front Right Swerve Drive - 3 | Neo (Rev)
 * Front Right Swerve Turn - 4 | Neo 550 (Rev)
 * Back Left Swerve Drive - 5 | Neo (Rev)
 * Back Left Swerve Turn - 6 | Neo 550 (Rev)
 * Back Right Swerve Drive - 7 | Neo  (Rev)
 * Back Right Swerve Turn - 8 | Neo 550 (Rev)
 * 
 * Top Wheel of Shooter - 9 (motor for the top wheel, physically this motor is actually on the bottom of the shooter) | Kraken (CTRE)
 * Bottom Wheel of Shooter - 10 | Falcon 500 (CTRE)
 * 
 * Intake Motor - 11 | Kraken (CTRE)
 * 
 * Blender Motor - 12 | Falcon 500 (CTRE)
 * 
 * Gyro (Pigeon2) - 20
 * PDH - 21
 * 
 */

//Motors for Swerve Drive aren't found here as they are managed by the Swerve Drive Modules in the SwerveDrive subsystem
public final class Hardware {
    public static final Pigeon2 gyro = new Pigeon2(20); 
    public static final GameController controller = new GameController(0);

    public static final TalonFX bottomShooterMotor = new TalonFX(10);
    public static final TalonFX topShooterMotor = new TalonFX(9); 

    // public static final TalonFX blenderMotor = new TalonFX(-1);

    //public static final TalonFX intakeMotor = new TalonFX(-1);

    //Not hardware related but this is the best class to put this in
    public static final NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
    public static final double CONTROLLER_DEADZONE = 0.12;


}
