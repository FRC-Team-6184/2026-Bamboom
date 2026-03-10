// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.commands.BlenderCommand;
import frc.robot.commands.IntakeInCommand;
import frc.robot.commands.IntakeOutCommand;
import frc.robot.commands.ShooterCommand;

import frc.robot.subsystems.BlenderSubsys;
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.Vision;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls). Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final BlenderSubsys kBlenderSubsystem;
  private final IntakeSubsys kIntakeSubsystem;
  private final ShooterSubsys kShooterSubsystem;
  private final SwerveSubsys kSwerveSubsystem;

  // Commands TODO: define these guys in the constructor, and make them final
  private BlenderCommand kBlenderCommand;
  private IntakeInCommand kIntakeInCommand;
  private IntakeOutCommand kIntakeOutCommand;
  private ShooterCommand kShooterCommand;

  /** Class constructor. Initializes subsystems, bindings, controllers, etc. */
  public RobotContainer() {
    configureBindings();

    // Init subsystems TODO: Encapsulate this stuff
    this.kBlenderSubsystem = new BlenderSubsys();
    this.kIntakeSubsystem = new IntakeSubsys();
    this.kShooterSubsystem = new ShooterSubsys();
    this.kSwerveSubsystem = new SwerveSubsys();
  }

  // Check the wpilib docs (Advanced Programming > Structuring a Command-Based Robot Project > Scroll down) for more information on this method
  private void configureBindings() {

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
      case "Blender":
        return kBlenderSubsystem;
      case "Intake":
        return kIntakeSubsystem;
      case "Shooter":
        return kShooterSubsystem;
    }

    System.out.println("Invalid subsystem name");
    return null; // Maybe change this so it throws an exception or something, but idk how to do custom exceptions. It doesn't matter much either way
  }
}
