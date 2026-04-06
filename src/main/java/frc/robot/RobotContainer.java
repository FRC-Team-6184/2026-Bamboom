// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotMap.Gyro;
import frc.robot.commands.blender.BlenderCommand;
import frc.robot.commands.flywheel.FlywheelCommand;
import frc.robot.commands.flywheel.FlywheelHighSpeedCommand;
import frc.robot.commands.flywheel.FlywheelLowSpeedCommand;
import frc.robot.commands.shooter.HighShooterRPMCommand;
import frc.robot.commands.intake.IntakeCommand;
import frc.robot.commands.intake.IntakePivotCommand;
import frc.robot.commands.intake.IntakePivotUpCommand;
import frc.robot.commands.intake.IntakePurgeCommand;
import frc.robot.commands.other.LockOnCommand;
import frc.robot.commands.shooter.LowShooterRPMCommand;
import frc.robot.commands.other.ResetGyroCommand;
import frc.robot.commands.shooter.ShooterCommand;
import frc.robot.commands.shooter.ShooterRPMControlCommand;
import frc.robot.commands.shooter.TempShooterCommand;
import frc.robot.commands.swerve.XFormationCommand;
import frc.robot.commands.intake.IntakePivotDownCommand;
import frc.robot.commands.intake.IntakeManagerCommand;
import frc.robot.commands.blender.BlenderCommand;
import frc.robot.commands.flywheel.FlywheelCommand;
import frc.robot.commands.flywheel.FlywheelHighSpeedCommand;
import frc.robot.commands.flywheel.FlywheelLowSpeedCommand;
import frc.robot.commands.intake.IntakeCommand;
import frc.robot.commands.intake.IntakeManagerCommand;
import frc.robot.commands.intake.IntakePivotCommand;
import frc.robot.commands.intake.IntakePivotDownCommand;
import frc.robot.commands.intake.IntakePivotUpCommand;
import frc.robot.commands.intake.IntakePurgeCommand;
import frc.robot.commands.other.LockOnCommand;
import frc.robot.commands.shooter.HighShooterRPMCommand;
import frc.robot.commands.shooter.LowShooterRPMCommand;
import frc.robot.commands.shooter.ShooterCommand;
import frc.robot.commands.shooter.ShooterRPMControlCommand;
import frc.robot.commands.shooter.TempShooterCommand;
import frc.robot.commands.swerve.XFormationCommand;
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.VisionSubsys;
import frc.robot.subsystems.ledSubsys;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls). Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // Subsystems
    private static final IntakeSubsys kIntakeSubsystem = new IntakeSubsys();
    private static final ShooterSubsys kShooterSubsystem = new ShooterSubsys();
    private static final SwerveSubsys kSwerveSubsystem = new SwerveSubsys();
    private static final VisionSubsys kVisionSubsystem = new VisionSubsys();
    private static final ledSubsys kLEDSubsystem = new ledSubsys();

    private final CommandXboxController mainController = RobotMap.Controller.XBOX;
    private final CommandPS5Controller codriveController = RobotMap.Controller.PS5;

    public static FlywheelHighSpeedCommand cmdFlywheelHigh = new FlywheelHighSpeedCommand(kShooterSubsystem);
    public static FlywheelLowSpeedCommand cmdFlywheelLow = new FlywheelLowSpeedCommand(kShooterSubsystem);

    private CommandScheduler scheduler = CommandScheduler.getInstance();

    private static boolean highSpeed = false;

    /** Class constructor. Initializes subsystems, bindings, controllers, etc. */
    public RobotContainer() {
        IntakePivotDownCommand cmdPivotDown = new IntakePivotDownCommand(kIntakeSubsystem);
        BlenderCommand cmdBlender = new BlenderCommand(kShooterSubsystem);
        IntakePivotUpCommand cmdPivotUp = new IntakePivotUpCommand(kIntakeSubsystem);
        configureBindings();

        NamedCommands.registerCommand("IntakePivotDownCommand", cmdPivotDown.withTimeout(Second.of(0.5)));
        NamedCommands.registerCommand("BlenderCommand", cmdBlender.withTimeout(Seconds.of(4.5)));
        NamedCommands.registerCommand("IntakePivotUpCommand", cmdPivotUp.withTimeout(Seconds.of(0.5)));

    }

    // Check the wpilib docs (Advanced Programming > Structuring a Command-Based Robot Project > Scroll down) for more information on this method
    private void configureBindings() {
        BlenderCommand cmdBlender = new BlenderCommand(kShooterSubsystem);
        HighShooterRPMCommand cmdHighSpeed = new HighShooterRPMCommand(kShooterSubsystem);
        LowShooterRPMCommand cmdLowSpeed = new LowShooterRPMCommand(kShooterSubsystem);
        IntakeCommand cmdIntake = new IntakeCommand(kIntakeSubsystem);
        // ShooterCommand cmdShooter = new ShooterCommand(kShooterSubsystem, kSwerveSubsystem);
        TempShooterCommand cmdShooter = new TempShooterCommand(kShooterSubsystem);
        IntakePurgeCommand cmdIntakePurge = new IntakePurgeCommand(kIntakeSubsystem);
        IntakePivotCommand cmdIntakePivot = new IntakePivotCommand(kIntakeSubsystem);
        XFormationCommand cmdXFormation = new XFormationCommand(kSwerveSubsystem, kLEDSubsystem);
        LockOnCommand cmdLockon = new LockOnCommand(kSwerveSubsystem);
        ShooterRPMControlCommand cmdFlywheelRPM = new ShooterRPMControlCommand(kShooterSubsystem);
        ResetGyroCommand cmdResetGyro = new ResetGyroCommand();

        mainController.x().toggleOnTrue(cmdXFormation);
        mainController.rightBumper().and(mainController.leftBumper()).whileTrue(cmdResetGyro);

        codriveController.L1().toggleOnTrue(cmdFlywheelRPM);

        codriveController.axisGreaterThan(3, 0.8).whileTrue(cmdBlender);

        codriveController.R1().toggleOnTrue(cmdIntake);
        codriveController.axisGreaterThan(4, 0.8).whileTrue(cmdIntakePurge);
        codriveController.triangle().whileTrue(cmdShooter);
        codriveController.circle().whileTrue(cmdLockon);

        codriveController.axisGreaterThan(5, 0.12).or(codriveController.axisLessThan(5, -0.12)).whileTrue(cmdIntakePivot); //TODO: make this go to a proportional intake pivot command

    }

    public SubsystemBase getSubsystem(String subsys) {
        switch (subsys) {
            case "Swerve":
                return kSwerveSubsystem;
            case "Intake":
                return kIntakeSubsystem;
            case "Shooter":
                return kShooterSubsystem;
            case "Vision":
                return kVisionSubsystem;
            case "LEDs":
                return kLEDSubsystem;
        }

        System.out.println("Invalid subsystem name");
        return null;
    }

    public static boolean isHighSpeed() {
        return highSpeed;
    }

    public static boolean notHighSpeed() {
        return !highSpeed;
    }

    public static void setHighSpeed(boolean nhighSpeed) {
        highSpeed = nhighSpeed;
    }
}
