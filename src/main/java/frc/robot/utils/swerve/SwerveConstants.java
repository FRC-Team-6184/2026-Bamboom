// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//Note from William H. :
//Stole this straight from the REV MaxSwerve Template, added additional notes and such 
//though to help make this more approachable and understandable

package frc.robot.utils.swerve;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class SwerveConstants {
    /**
     * Generic constants regarding the overall use of Swerve, including the CAN IDs of the motors.
     */
    public static final class DriveConstants {
        // Driving Parameters - Note that these are not the maximum capable speeds of
        // the robot, rather the allowed maximum speeds
        /**
         * Hard set max speed for the overall robot
         */
        public static final double kMaxSpeedMetersPerSecond = 4.8;
        /**
         * Hard set max turning/rotation speed for the robot
         */
        public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

        // Chassis configuration
        /**
         * This assumes your robot is square/rectangle shaped (which it usually is)
         * kTrackWidth is the distance between the left and right wheels, 
         * I'm using the distance between the left and right drive motors for this
         * kWheelBase is the distance between the front and back wheels, 
         * I'm using the same reference point for this, centers of motors. 
         */
        public static final double kTrackWidth = Units.inchesToMeters(21.525); //This may be off actually, but we'll see. Measurement taken via CAD
        // Distance between centers of right and left wheels on robot
        public static final double kWheelBase = Units.inchesToMeters(21.525);
        // Distance between front and back wheels on robot
        /**
         * You shouldn't have to touch this at all ever, 
         * this is just making sure the robot knows where the wheels are relative to the center of the robot.
         */
        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

        // Angular offsets of the modules relative to the chassis in radians
        /**
         * Yet another way for the robot to keep reference of where the modules are in reference to the center of the robot.
         * You shouldn't have to touch this if you have a square frame, possibly if it's rectangular.
         */
        public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
        public static final double kFrontRightChassisAngularOffset = 0;
        public static final double kBackLeftChassisAngularOffset = Math.PI;
        public static final double kBackRightChassisAngularOffset = Math.PI / 2;

        // SPARK MAX CAN IDs
        /**
         * Make sure these line up with your robot's!
         * For Team 6184, these have already been set.
         */
        public static final int kFrontLeftDrivingCanId = 1;
        public static final int kRearLeftDrivingCanId = 5;
        public static final int kFrontRightDrivingCanId = 3;
        public static final int kRearRightDrivingCanId = 7;

        public static final int kFrontLeftTurningCanId = 2;
        public static final int kRearLeftTurningCanId = 6;
        public static final int kFrontRightTurningCanId = 4;
        public static final int kRearRightTurningCanId = 8;

        public static final boolean kGyroReversed = false;
    }

    /**
     * This is concerning the actual math of some of the mechanisms of the swerve modules.
     * You shouldn't ever need to touch anything other than kDrivingMotorPinionTeeth.
     * 
     * Only do so if you know what you're doing or if you have swapped out of the pinion gears.
     */
    public static final class ModuleConstants {
        // The MAXSwerve module can be configured with one of three pinion gears: 12T,
        // 13T, or 14T. This changes the drive speed of the module (a pinion gear with
        // more teeth will result in a robot that drives faster).
        public static final int kDrivingMotorPinionTeeth = 14;

        // Calculations required for driving motor conversion factors and feed forward
        public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
        public static final double kWheelDiameterMeters = 0.0762;
        public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
        // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
        // teeth on the bevel pinion
        public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
        public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
                / kDrivingMotorReduction;
    }

    public static final class OIConstants {
        public static final int kDriverControllerPort = 0;
        public static final double kDriveDeadband = 0.05;
    }

    /**
     * These are constants that are only relevant during Autonomous Mode.
     * Any duplicate settings that exist elsewhere are overriden by the ones here during auto.
     */
    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;

        // Constraint for the motion profiled robot angle controller
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    }

    /**
     * So, I think this is the max speed of the big ol' Neo with no load, but I frankly have not a single clue.
     * I'm just trusting this number because I don't know what this is.
     */
    public static final class NeoMotorConstants {
        public static final double kFreeSpeedRpm = 5676;
    }
}
