// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Blender;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls). Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private Blender kBlenderSubsystem;
  private Intake kIntakeSubsystem;
  private Shooter kShooterSubsystem;
  private SwerveDrive kSwerveSubsystem;

  /** Class constructor. Initializes subsystems, bindings, controllers, etc. */
  public RobotContainer() {
    configureBindings();
    initializeSubsystems();
  }

  // Check the wpilib docs (Advanced Programming > Structuring a Command-Based Robot Project > Scroll down) for more information on this method
  private void configureBindings() {
    // Add bindings here
  }

  /** Declare subsystems at the top of the class, then define them here */
  private void initializeSubsystems() {
    kBlenderSubsystem = new Blender();
    kIntakeSubsystem = new Intake();
    kShooterSubsystem = new Shooter();
    kSwerveSubsystem = new SwerveDrive();
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
