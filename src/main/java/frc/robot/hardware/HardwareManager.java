package frc.robot.hardware;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;

/**  Hardware CAN IDs: (Verify that all are correct sometime)
 * Front Left Swerve Drive -  1 | Neo     (Rev Robotics) 
 * Front Left Swerve Turn -   2 | Neo 550 (Rev Robotics) 
 * Front Right Swerve Drive - 3 | Neo     (Rev Robotics) 
 * Front Right Swerve Turn -  4 | Neo 550 (Rev Robotics) 
 * Back Left Swerve Drive -   5 | Neo     (Rev Robotics) 
 * Back Left Swerve Turn -    6 | Neo 550 (Rev Robotics) 
 * Back Right Swerve Drive -  7 | Neo     (Rev Robotics) 
 * Back Right Swerve Turn -   8 | Neo 550 (Rev Robotics) 
 * 
 * 
 * Top Wheel of Shooter -     9  | Kraken     (CTRE) (Motor for the top wheel, physically this motor is actually on the bottom of the shooter)
 * Bottom Wheel of Shooter -  13 | Falcon 500 (CTRE) 
 * 
 * Intake Motor -             11 | Kraken     (CTRE) 
 * 
 * Blender Motor -            12 | Falcon 500 (CTRE) 
 * 
 * Gyro (Pigeon2) -           20
 * PDH -                      21
 */

public final class HardwareManager {
    public final class Gyro {
        // Should I put this in the CAN_IDs inner class?? Not sure if its a motor controller or not
        public static final int GYRO_ID = 20;

        public static final Pigeon2 GYRO = new Pigeon2(GYRO_ID); 
    }

    public final class Controller {
        // Xbox port
        public static final int XBOX_P = 0;

        public static final double CONTROLLER_DEADZONE = 0.12; // Maybe move this elsewhere

        public static final GameController GAME_CONTROLLER = new GameController(XBOX_P);
    }

    public final class CAN_IDs {
        // Shooter
        public static final int BOTTOM_SHOOTER_WHEEL_ID = 13;
        public static final int TOP_SHOOTER_WHEEL_ID =  9;

        // Intake
        public static final int INTAKE_MOTOR_ID = 11;

        // Blender
        public static final int BLENDER_MOTOR_ID = 12;

        // Swerve
        public static final int FL_DRIVE_MOTOR_ID = 1;
        public static final int FL_TURN_MOTOR_ID =  2;

        public static final int FR_DRIVE_MOTOR_ID = 3;
        public static final int FR_TURN_MOTOR_ID =  4;

        public static final int BL_DRIVE_MOTOR_ID = 5;
        public static final int BL_TURN_MOTOR_ID =  6;

        public static final int BR_DRIVE_MOTOR_ID = 7;
        public static final int BR_TURN_MOTOR_ID =  8;
    }

    public final class MotorControllers {
        // Shooter Motors
        public static final TalonFX BOTTOM_SHOOTER_WHEEL = new TalonFX(CAN_IDs.BOTTOM_SHOOTER_WHEEL_ID);
        public static final TalonFX TOP_SHOOTER_WHEEL = new TalonFX(CAN_IDs.TOP_SHOOTER_WHEEL_ID); 

        // Swerve
            // Left-side Drive Motors
        public static final SparkMax FL_DRIVE_MOTOR = new SparkMax(CAN_IDs.FL_DRIVE_MOTOR_ID, MotorType.kBrushless);
        public static final SparkMax BL_DRIVE_MOTOR = new SparkMax(CAN_IDs.BL_DRIVE_MOTOR_ID, MotorType.kBrushless);
            // Left-side Turn Motors
        public static final SparkMax FL_TURN_MOTOR = new SparkMax(CAN_IDs.FL_TURN_MOTOR_ID, MotorType.kBrushless);
        public static final SparkMax BL_TURN_MOTOR = new SparkMax(CAN_IDs.BL_TURN_MOTOR_ID, MotorType.kBrushless);


            // Right-side Drive Motors
        public static final SparkMax FR_DRIVE_MOTOR = new SparkMax(CAN_IDs.FR_DRIVE_MOTOR_ID, MotorType.kBrushless);
        public static final SparkMax BR_DRIVE_MOTOR = new SparkMax(CAN_IDs.BR_DRIVE_MOTOR_ID, MotorType.kBrushless);
            // Right-side Turn Motors
        public static final SparkMax FR_TURN_MOTOR = new SparkMax(CAN_IDs.FR_TURN_MOTOR_ID, MotorType.kBrushless);
        public static final SparkMax BR_TURN_MOTOR = new SparkMax(CAN_IDs.BR_TURN_MOTOR_ID, MotorType.kBrushless);

        // Intake
        public static final TalonFX INTAKE_MOTOR = new TalonFX(CAN_IDs.INTAKE_MOTOR_ID); // Check to make sure this ID is right

        // Blender
        public static final TalonFX BLENDER_MOTOR = new TalonFX(CAN_IDs.BLENDER_MOTOR_ID); // Check to make sure this ID is right
    }

    public final class Chassis {
        /**
         * This assumes your robot is rectangular.
         * TRACK_WIDTH is the distance between the left and right wheels, 
         * I'm using the distance between the left and right drive motors for this.
         * WHEEL_BASE is the distance between the front and back wheels, 
         * I'm using the same reference point for this: centers of motors. 
         */

        // Distance between centers of right and left wheels on robot
        public static final double TRACK_WIDTH = Units.inchesToMeters(21.525); // This may be off, but we'll see. Measurement taken via CAD

        // Distance between front and back wheels on robot
        public static final double WHEEL_BASE = Units.inchesToMeters(21.525);
    }

    // Move this somewhere else. It's not hardware
    public static final NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
    
    private HardwareManager() {} // Overrides default constructor. Don't want anybody instantiating this class, even though likely no one would.
}
