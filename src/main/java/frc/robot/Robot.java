// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Blender;
import frc.robot.subsystems.Intake;

import frc.robot.RobotContainer;

public class Robot extends TimedRobot {
  // Subsystems
  private SwerveDrive SwerveDrive;
  private Shooter Shooter;
  private Blender Blender;
  private Intake Intake;

  // idk
  private Command autonomousCommand;
  private RobotContainer robotContainer;

  // TODO: Set up smart dashboard for easy testing and switching which motors to run at runtime

  /** Robot Constructor. Instantiates RobotContainer and performs various initializations */
  public Robot() {
    robotContainer = new RobotContainer();

    SwerveDrive = (SwerveDrive) robotContainer.getSubsystem("Swerve");
    Shooter = (Shooter) robotContainer.getSubsystem("Shooter");
    Blender = (Blender) robotContainer.getSubsystem("Blender");
    Intake = (Intake) robotContainer.getSubsystem("Intake");
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); // Ask William if you have any questions about this line 
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your{@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // if (m_autonomousCommand != null) {
    // m_autonomousCommand.cancel();
    // }

    // CommandScheduler.getInstance().schedule(SwerveDrive.teleopDrive());
    // CommandScheduler.getInstance().schedule(Shooter.teleopShoot());
    // CommandScheduler.getInstance().schedule(Blender.teleopBlender());
    CommandScheduler.getInstance().schedule(Intake.teleopIntake());
  }

  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
