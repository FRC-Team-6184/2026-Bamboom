// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.BlenderCommand;
import frc.robot.commands.FlywheelCommand;
import frc.robot.commands.HighShooterRPMCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.IntakePivotUpCommand;
import frc.robot.commands.IntakePurgeCommand;
import frc.robot.commands.LowShooterRPMCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.IntakePivotDownCommand;
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.VisionSubsys;
import static edu.wpi.first.units.Units.Seconds;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls). Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // Subsystems
    private final IntakeSubsys kIntakeSubsystem = new IntakeSubsys();
    private final ShooterSubsys kShooterSubsystem = new ShooterSubsys();
    private final SwerveSubsys kSwerveSubsystem = new SwerveSubsys();
    private final VisionSubsys kVisionSubsystem = new VisionSubsys();

    private final CommandXboxController mainController = RobotMap.Controller.XBOX;
    private final CommandPS5Controller codriveController = RobotMap.Controller.PS5;

    // Commands TODO: define these guys in the constructor, and make them final
    // private final BlenderCommand kBlenderCommand;
    // private final IntakeInCommand kIntakeInCommand;
    // private final IntakeOutCommand kIntakeOutCommand;
    // private final ShooterCommand kShooterCommand;

    private CommandScheduler scheduler = CommandScheduler.getInstance();

    /** Class constructor. Initializes subsystems, bindings, controllers, etc. */
    public RobotContainer() {
        configureBindings();
    }

    // Check the wpilib docs (Advanced Programming > Structuring a Command-Based Robot Project > Scroll down) for more information on this method
    private void configureBindings() {
        // IntakePivotUpCommand cmdIntakeUp = new IntakePivotUpCommand(kIntakeSubsystem);
        // IntakePivotDownCommand cmdIntakeDown = new IntakePivotDownCommand(kIntakeSubsystem);
        FlywheelCommand cmdFlywheel = new FlywheelCommand(kShooterSubsystem);
        BlenderCommand cmdBlender = new BlenderCommand(kShooterSubsystem);
        HighShooterRPMCommand cmdHighSpeed = new HighShooterRPMCommand(kShooterSubsystem);
        LowShooterRPMCommand cmdLowSpeed = new LowShooterRPMCommand(kShooterSubsystem);
        IntakeCommand cmdIntake = new IntakeCommand(kIntakeSubsystem);
        ShooterCommand cmdShooter = new ShooterCommand(kShooterSubsystem, kSwerveSubsystem);
        IntakePurgeCommand cmdIntakePurge = new IntakePurgeCommand(kIntakeSubsystem);
        IntakePivotUpCommand cmdIntakeUp = new IntakePivotUpCommand(kIntakeSubsystem);
        IntakePivotDownCommand cmdIntakeDown = new IntakePivotDownCommand(kIntakeSubsystem);

        codriveController.L1().toggleOnTrue(cmdFlywheel);
        codriveController.axisGreaterThan(3, 0.8).whileTrue(cmdBlender);

        codriveController.povUp().onTrue(cmdHighSpeed);
        codriveController.povDown().onTrue(cmdLowSpeed);

        codriveController.R1().toggleOnTrue(cmdIntake);
        codriveController.axisGreaterThan(4, 0.8).whileTrue(cmdIntakePurge);
        codriveController.axisLessThan(5, -0.8).onTrue(cmdIntakeDown);
        codriveController.axisGreaterThan(5, 0.8).onTrue(cmdIntakeUp);
        codriveController.triangle().whileTrue(cmdShooter);

        codriveController.axisGreaterThan(0, 0.12).or(codriveController.axisLessThan(0, -0.12)).whileTrue(cmdIntakeDown) //TODO: make this go to a proportional intake pivot command

    }

    // API to get commands, subsytems, etc.
    /** Returns the teleop command for the blender subsystem */
    public void getTeleopBlender() {

    }

    /** Returns the teleop command for the intake subsystem */
    public void getTeleopIntake() {}

    /** Returns the teleop command for the shooter subsystem */
    public void getTeleopShooter() {}

    /** Returns the teleop command for the swerve drive subsystem */
    public void getTeleopDrive() {}

    /** Returns the autonomous command for the blender subsystem */
    public void getAutonomousBlender() {}

    /** Returns the teleop command for the intake subsystem */
    public void getAutonomousIntake() {}

    /** Returns the teleop command for the shooter subsystem */
    public void getAutonomousShooter() {}

    /** Returns the teleop command for the swerve drive subsystem */
    public void getAutonomousDrive() {}

    // TODO: Use enums here instead of a String subsys, that way you can't accidentally type in "sweve" instead of "swerve" and not know somethings wrong until runtime
    /** Returns the object of a subsystem based on String subsys */
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
        }

        System.out.println("Invalid subsystem name");
        return null; // Maybe change this so it throws an exception or something, but idk how to do custom exceptions. It doesn't matter much either way
    }
}
