// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.VisionSubsys;
import frc.robot.subsystems.ledSubsys;
import frc.robot.RobotMap.SoftwareObjects;
import frc.robot.commands.swerve.SwerveTeleopDriveCommand;
import frc.robot.subsystems.AutonomousSubsys;
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
  private final ledSubsys LEDs;
  private final AutonomousSubsys Auto;

  private PathPlannerAuto autoCommand;
  private final Command swerveDriveCommand;

  private final NetworkTableInstance network = SoftwareObjects.networkTableInstance;

  private DoubleEntry flyWheelDest = network.getDoubleTopic("Flywheel Destination").getEntry(0.0);
  private DoubleEntry kickerDest = network.getDoubleTopic("Kicker Destination").getEntry(0.0);
  private DoubleEntry blenderDest = network.getDoubleTopic("Blender Destination").getEntry(0.0);
  private DoubleEntry intakeDest = network.getDoubleTopic("Intake Destination").getEntry(0.0);

  /** Robot Constructor. Instantiates RobotContainer and performs various initializations */
  public Robot() {
    // Robot Container
    robotContainer = new RobotContainer();

    // Subsystems
    SwerveDrive = (SwerveSubsys) robotContainer.getSubsystem("Swerve");
    Shooter = (ShooterSubsys) robotContainer.getSubsystem("Shooter");
    Intake = (IntakeSubsys) robotContainer.getSubsystem("Intake");
    Vision = (VisionSubsys) robotContainer.getSubsystem("Vision");
    LEDs = (ledSubsys) robotContainer.getSubsystem("LEDs");
    Auto = (AutonomousSubsys) robotContainer.getSubsystem("Auto");

    // Commands
    swerveDriveCommand = SwerveDrive.teleopDrive();

    // autoCommand = new PathPlannerAuto("pfield");

    flyWheelDest.set(0.0);
    kickerDest.set(0.0);
    blenderDest.set(0.0);
    intakeDest.set(0.0);

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

    autoCommand = Auto.getSelectedAuto();
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

    RobotMap.SoftwareObjects.poseEstimator.resetPosition(new Rotation3d(new Rotation2d(-180)), new SwerveModulePosition[] {RobotMap.SoftwareObjects.FRONT_LEFT_MODULE.getPosition(), RobotMap.SoftwareObjects.FRONT_RIGHT_MODULE.getPosition(), RobotMap.SoftwareObjects.BACK_LEFT_MODULE.getPosition(), RobotMap.SoftwareObjects.BACK_RIGHT_MODULE.getPosition()}, new Pose3d(new Pose2d(Meters.convertFrom(651 - 82, Inches), Meters.convertFrom(158.84, Inches), new Rotation2d(-180))));
    CommandScheduler.getInstance().schedule(swerveDriveCommand);
    // CommandScheduler.getInstance().schedule(Intake.teleopIntake());
    // CommandScheduler.getInstance().schedule(Vision);
  }

  @Override
  public void teleopPeriodic() {

  }

  // DigitalInput intakeLimitSwitch = new DigitalInput(6);
  // BooleanEntry intakeEntry = SoftwareObjects.networkTableInstance.getBooleanTopic("Intake Limit").getEntry(false);

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    // intakeEntry.set(false);
    // CommandScheduler.getInstance().schedule(SwerveDrive.teleopDrive());
    // CommandScheduler.getInstance().schedule(Shooter.testShoot()); // This is jank but its fine for now. Will be removed in the future
    // CommandScheduler.getInstance().schedule(Blender.testBlender());
    // CommandScheduler.getInstance().schedule(Intake.teleopIntake());
  }

  // double power = 0;



  @Override
  public void testPeriodic() {
    // intakeEntry.set(intakeLimitSwitch.get());
    Shooter.setFlywheelRPMDest(flyWheelDest.get() / 60.0);
    Shooter.setBlenderRPMDest(blenderDest.get() / 60.0);
    Shooter.setKickerRPMDest(kickerDest.get() / 60.0);
    Intake.setIntakeSpeed(intakeDest.get() / 60.0);

  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
