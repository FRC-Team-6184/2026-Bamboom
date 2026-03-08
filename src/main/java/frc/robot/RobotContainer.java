// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;

import frc.robot.constants.RobotMap.Controller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Blender;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

/* TODO: Figure out how to make variables final, while still initalizing them in the constructor and declaring them at the top of the class */

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
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

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
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
  public void getAutonomousCommand() {
    // TODO: Actually implement this method
  }

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
    return null;
  }
}
