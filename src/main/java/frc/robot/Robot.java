// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.IntakeSubsys;

public class Robot extends TimedRobot {
  // Subsystems
  private SwerveSubsys SwerveDrive;
  private ShooterSubsys Shooter;
  private IntakeSubsys Intake;

  // Robot container
  private RobotContainer robotContainer;

  // TODO: Set up smart dashboard for easy testing and switching which motors to run at runtime

  /** Robot Constructor. Instantiates RobotContainer and performs various initializations */
  public Robot() {
    robotContainer = new RobotContainer();

    // TODO: Figure out a type safer way to cast the returned SubsystemBase back into its respective subclass
    SwerveDrive = (SwerveSubsys) robotContainer.getSubsystem("Swerve");
    Shooter = (ShooterSubsys) robotContainer.getSubsystem("Shooter");
    Intake = (IntakeSubsys) robotContainer.getSubsystem("Intake");

    // SwerveDrive = robotContainer.getSubsystem("Swerve") instanceof SwerveSubsys ? (SwerveSubsys) robotContainer.getSubsystem("Swerve") : null;
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
    // TODO: Change these to schedule commands from RobotContainer rather than the subsystems directly
    CommandScheduler.getInstance().cancelAll(); // idk if we need this, I didn't want think too hard to ensure stuff doesnt break.

    CommandScheduler.getInstance().schedule(SwerveDrive.teleopDrive());
    CommandScheduler.getInstance().schedule(Intake.teleopIntake());
  }

  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    // CommandScheduler.getInstance().schedule(SwerveDrive.teleopDrive());
    // CommandScheduler.getInstance().schedule(Shooter.testShoot()); // This is jank but its fine for now. Will be removed in the future
    // CommandScheduler.getInstance().schedule(Blender.testBlender());
    // CommandScheduler.getInstance().schedule(Intake.teleopIntake());
  }

  // double power = 0;

  @Override
  public void testPeriodic() {
    // power += 0.0001;
    // if (power > 1.0) {
    //   power = 0;
    // }
    // System.out.println(power);
    // RobotMap.MotorControllers.FL_DRIVE_MOTOR.set(power);

  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
