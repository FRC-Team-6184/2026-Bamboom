// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.VisionSubsys;
import frc.robot.subsystems.LEDSubsys;
import frc.robot.RobotMap.SoftwareObjects;
import frc.robot.commands.swerve.SwerveTeleopDriveCommand;
import frc.robot.subsystems.IntakeSubsys;


// TODO: Store robot angle as a decimal at the beginning of autonomous
// Find the offset between that angle and the angle of the bot at the end of autonomous
// Use that offset to correctly field-orient the swerve drive

// TODO: Hook up status of things running to dashboard
// ie: when shooter is running, when intake is running, etc. etc.

// TODO: Set up smart dashboard for easy testing and switching which motors to run at runtime
public class Robot extends TimedRobot {
  // Robot Container
  private final RobotContainer robotContainer;

  // Subsystems
  private final SwerveSubsys SwerveDrive;
  private final ShooterSubsys Shooter;
  private final IntakeSubsys Intake;
  private final VisionSubsys Vision;
  private final LEDSubsys LEDs;

  private final PathPlannerAuto autoCommand;
  private final SwerveTeleopDriveCommand swerveDriveCommand;

  /** Robot Constructor. Instantiates RobotContainer and performs various initializations */
  public Robot() {
    // Robot Container
    robotContainer = new RobotContainer();

    // Subsystems
    SwerveDrive = (SwerveSubsys) robotContainer.getSubsystem("Swerve");
    Shooter = (ShooterSubsys) robotContainer.getSubsystem("Shooter");
    Intake = (IntakeSubsys) robotContainer.getSubsystem("Intake");
    Vision = (VisionSubsys) robotContainer.getSubsystem("Vision");
    LEDs = (LEDSubsys) robotContainer.getSubsystem("LEDs");

    // Commands
    swerveDriveCommand = (SwerveTeleopDriveCommand) robotContainer.getCommand("Command_SwerveDrive");

    autoCommand = new PathPlannerAuto("TestAutocmd");

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
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    // PathPlannerAuto.
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    RobotMap.Gyro.GYRO.reset();

    CommandScheduler.getInstance().schedule(autoCommand);
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    RobotMap.Gyro.GYRO.reset();
    // TODO: Change these to schedule commands from RobotContainer rather than the subsystems directly
    CommandScheduler.getInstance().cancelAll(); // idk if we need this, I didn't want think too hard to ensure stuff doesnt break.

    CommandScheduler.getInstance().schedule(swerveDriveCommand);
    // CommandScheduler.getInstance().schedule(Intake.teleopIntake());
    // CommandScheduler.getInstance().schedule(Vision);
  }

  @Override
  public void teleopPeriodic() {

  }

  DigitalInput intakeLimitSwitch = new DigitalInput(6);
  BooleanEntry intakeEntry = SoftwareObjects.networkTableInstance.getBooleanTopic("Intake Limit").getEntry(false);

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    intakeEntry.set(false);
    // CommandScheduler.getInstance().schedule(SwerveDrive.teleopDrive());
    // CommandScheduler.getInstance().schedule(Shooter.testShoot()); // This is jank but its fine for now. Will be removed in the future
    // CommandScheduler.getInstance().schedule(Blender.testBlender());
    // CommandScheduler.getInstance().schedule(Intake.teleopIntake());
  }

  // double power = 0;



  @Override
  public void testPeriodic() {
    intakeEntry.set(intakeLimitSwitch.get());

  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
