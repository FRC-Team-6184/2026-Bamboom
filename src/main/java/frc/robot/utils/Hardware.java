package frc.robot.utils;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.utils.swerve.SwerveConstants.DriveConstants;

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

public final class Hardware {
    // Controller.. and gyro for swerve
    public static final Pigeon2 gyro = new Pigeon2(20); 
    public static final GameController controller = new GameController(0);

    // Shooter Motors
    public static final TalonFX bottomShooterWheel = new TalonFX(13);
    public static final TalonFX topShooterWheel = new TalonFX(9); 

    // Swerve
        // Left-side Drive Motors
    public static final SparkMax frontLeftDriveMotor = new SparkMax(DriveConstants.kFrontLeftDrivingCanId, MotorType.kBrushless);
    public static final SparkMax rearLeftDriveMotor = new SparkMax(DriveConstants.kRearLeftDrivingCanId, MotorType.kBrushless);
        // Left-side Turn Motors
    public static final SparkMax frontLeftTurnMotor = new SparkMax(DriveConstants.kFrontLeftTurningCanId, MotorType.kBrushless);
    public static final SparkMax rearLeftTurnMotor = new SparkMax(DriveConstants.kRearLeftTurningCanId, MotorType.kBrushless);


        // Right-side Drive Motors
    public static final SparkMax frontRightDriveMotor = new SparkMax(DriveConstants.kFrontRightDrivingCanId, MotorType.kBrushless);
    public static final SparkMax rearRightDriveMotor = new SparkMax(DriveConstants.kRearRightDrivingCanId, MotorType.kBrushless);
        // Right-side Turn Motors
    public static final SparkMax frontRightTurnMotor = new SparkMax(DriveConstants.kFrontRightTurningCanId, MotorType.kBrushless);
    public static final SparkMax rearRightTurnMotor = new SparkMax(DriveConstants.kRearRightTurningCanId, MotorType.kBrushless);

    // Blender
        public static final TalonFX placeholder = new TalonFX(-1); // Change this arg later to I think CAN ID?

    // Intake
        public static final TalonFX placeholder1 = new TalonFX(-1); // Chang this arg later to I think CAN ID?

    // Miscellaneous
        public static final NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
        public static final double CONTROLLER_DEADZONE = 0.12;
}
