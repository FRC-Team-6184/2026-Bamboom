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
import frc.robot.commands.intake.AutonomousIntakeDownCommand;
import frc.robot.commands.intake.AutonomousStartIntakeCommand;
import frc.robot.commands.intake.IntakeCommand;
import frc.robot.commands.intake.IntakePivotCommand;
import frc.robot.commands.intake.IntakePivotUpCommand;
import frc.robot.commands.intake.IntakePurgeCommand;
import frc.robot.commands.other.LockOnCommand;
import frc.robot.commands.shooter.LowShooterRPMCommand;
import frc.robot.commands.shooter.ShootAtSpeedCommand;
import frc.robot.commands.other.ResetGyroCommand;
import frc.robot.commands.shooter.ShooterCommand;
import frc.robot.commands.shooter.ShooterRPMControlCommand;
import frc.robot.commands.shooter.TempShooterCommand;
import frc.robot.commands.swerve.SwerveTeleopDriveCommand;
import frc.robot.commands.swerve.XFormationCommand;
import frc.robot.commands.intake.IntakePivotDownCommand;
import frc.robot.commands.intake.IntakePivotLimitSwitchCommand;
import frc.robot.commands.intake.IntakeManagerCommand;
import frc.robot.subsystems.AutonomousSubsys;
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.VisionSubsys;
import frc.robot.subsystems.ledSubsys;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls). Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // Controllers
    private final CommandXboxController mainController;
    private final CommandPS5Controller codriveController;

    // Subsystems
    private final IntakeSubsys kIntakeSubsystem;
    private final ShooterSubsys kShooterSubsystem;
    private final SwerveSubsys kSwerveSubsystem;
    private final VisionSubsys kVisionSubsystem;
    private final ledSubsys kLEDSubsystem;
    private final AutonomousSubsys kAutoSubsystem;

    // Commands
    FlywheelHighSpeedCommand cmdFlywheelHigh;
    FlywheelLowSpeedCommand cmdFlywheelLow;
    IntakePivotDownCommand cmdPivotDown;
    BlenderCommand cmdBlender;
    IntakePivotUpCommand cmdPivotUp;
    HighShooterRPMCommand cmdHighSpeed;
    LowShooterRPMCommand cmdLowSpeed;
    IntakeCommand cmdIntake;
    ShooterCommand cmdShooter;
    TempShooterCommand cmdTempShooter;
    IntakePurgeCommand cmdIntakePurge;
    IntakePivotCommand cmdIntakePivot;
    XFormationCommand cmdXFormation;
    ShooterRPMControlCommand cmdFlywheelRPM;
    ResetGyroCommand cmdResetGyro;
    SwerveTeleopDriveCommand cmdSwerveTeleop;
    IntakePivotLimitSwitchCommand cmdLimitSwitchPivot;
    ShooterRPMControlCommand cmdIncreaseRPM;
    ShooterRPMControlCommand cmdDecreaseRPM;
    LockOnCommand cmdLockon;

    ShootAtSpeedCommand cmdFlywheelUp;
    ShootAtSpeedCommand cmdFlywheelLeft;
    ShootAtSpeedCommand cmdFlywheelRight;
    ShootAtSpeedCommand cmdFlywheelDown;


    AutonomousIntakeDownCommand cmdAutoIntakePivot;
    AutonomousStartIntakeCommand cmdAutoStartIntake;
    BlenderCommand cmdAutoBlender;


    private static boolean highSpeed = false;

    /** Class constructor. Initializes subsystems, bindings, controllers, etc. */
    public RobotContainer() {

        mainController = RobotMap.Controller.XBOX;
        codriveController = RobotMap.Controller.PS5;

        kIntakeSubsystem = new IntakeSubsys();
        kShooterSubsystem = new ShooterSubsys();
        kSwerveSubsystem = new SwerveSubsys();
        kVisionSubsystem = new VisionSubsys();
        kLEDSubsystem = new ledSubsys();

        configureCommands(); //this absolutely needs to bve done before AutoSubsystem

        kAutoSubsystem = new AutonomousSubsys();

        configureBindings();
    }

    private void configureCommands() {
        cmdSwerveTeleop = new SwerveTeleopDriveCommand(kSwerveSubsystem);
        cmdFlywheelHigh = new FlywheelHighSpeedCommand(kShooterSubsystem);
        cmdFlywheelLow = new FlywheelLowSpeedCommand(kShooterSubsystem);
        cmdPivotDown = new IntakePivotDownCommand(kIntakeSubsystem);
        cmdBlender = new BlenderCommand(kShooterSubsystem, kIntakeSubsystem);
        cmdPivotUp = new IntakePivotUpCommand(kIntakeSubsystem);
        cmdHighSpeed = new HighShooterRPMCommand(kShooterSubsystem);
        cmdLowSpeed = new LowShooterRPMCommand(kShooterSubsystem);
        cmdIntake = new IntakeCommand(kIntakeSubsystem);
        cmdShooter = new ShooterCommand(kShooterSubsystem, kSwerveSubsystem);
        cmdTempShooter = new TempShooterCommand(kShooterSubsystem);
        cmdIntakePurge = new IntakePurgeCommand(kIntakeSubsystem);
        cmdIntakePivot = new IntakePivotCommand(kIntakeSubsystem);
        cmdXFormation = new XFormationCommand(kSwerveSubsystem, kLEDSubsystem);
        cmdLockon = new LockOnCommand(kSwerveSubsystem, kVisionSubsystem);
        cmdFlywheelRPM = new ShooterRPMControlCommand(kShooterSubsystem, 0);
        cmdResetGyro = new ResetGyroCommand();
        cmdLimitSwitchPivot = new IntakePivotLimitSwitchCommand(kIntakeSubsystem);
        cmdIncreaseRPM = new ShooterRPMControlCommand(kShooterSubsystem, 100.0 / 60.0);
        cmdDecreaseRPM = new ShooterRPMControlCommand(kShooterSubsystem, -100.0 / 60.0);

        cmdFlywheelUp = new ShootAtSpeedCommand(kShooterSubsystem, 2700 / 60.0);
        cmdFlywheelDown = new ShootAtSpeedCommand(kShooterSubsystem, 4500 / 60.0);
        cmdFlywheelLeft = new ShootAtSpeedCommand(kShooterSubsystem, 3200 / 60.0);
        cmdFlywheelRight = new ShootAtSpeedCommand(kShooterSubsystem, 3450 / 60.0);

        cmdAutoIntakePivot = new AutonomousIntakeDownCommand(kIntakeSubsystem);
        cmdAutoStartIntake = new AutonomousStartIntakeCommand(kIntakeSubsystem);
        cmdAutoBlender = new BlenderCommand(kShooterSubsystem, kIntakeSubsystem);



        //TODO: I think these were glitching things out, and these need to be done in a more robust and sensible way anyways
        NamedCommands.registerCommand("AutoIntakeDown", cmdAutoIntakePivot);
        NamedCommands.registerCommand("AutoIntakeStart", cmdAutoStartIntake);
        NamedCommands.registerCommand("BlenderCommand", cmdAutoBlender.withTimeout(Seconds.of(4.0)));
        // NamedCommands.registerCommand("IntakePivotUpCommand", cmdPivotUp.withTimeout(Seconds.of(0.5)));
    }

    // Check the wpilib docs (Advanced Programming > Structuring a Command-Based Robot Project > Scroll down) for more information on this method
    private void configureBindings() {
        mainController.x().toggleOnTrue(cmdXFormation);
        mainController.rightBumper().and(mainController.leftBumper()).whileTrue(cmdResetGyro);
        mainController.b().whileTrue(cmdLockon);

        codriveController.povUp().onTrue(cmdIncreaseRPM);
        codriveController.povDown().onTrue(cmdDecreaseRPM);

        codriveController.axisGreaterThan(3, 0.8).whileTrue(cmdBlender);

        codriveController.L1().whileTrue(cmdFlywheelHigh);
        codriveController.R1().toggleOnTrue(cmdIntake);
        codriveController.axisGreaterThan(4, 0.8).whileTrue(cmdIntakePurge);


        // codriveController.axisGreaterThan(5, 0.12).or(codriveController.axisLessThan(5, -0.12)).whileTrue(cmdIntakePivot); //TODO: make this go to a proportional intake pivot command

        codriveController.axisLessThan(5, -0.6).whileFalse(cmdLimitSwitchPivot).whileTrue(cmdIntakePivot);

        // codriveController.povUp().onTrue(cmdIncreaseRPM);
        // codriveController.povDown().onTrue(cmdDecreaseRPM);

        codriveController.povUp().whileTrue(cmdFlywheelUp);
        codriveController.povDown().whileTrue(cmdFlywheelDown);
        codriveController.povLeft().whileTrue(cmdFlywheelLeft);
        codriveController.povRight().whileTrue(cmdFlywheelRight);


        // codriveController.pov
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
            case "Auto":
                return kAutoSubsystem;
        }

        System.out.println("Invalid subsystem name. See RobotContainer.java");
        return null;
    }

    public Command getCommand(String command) {
        switch (command) {
            case "Command_SwerveDrive":
                return cmdSwerveTeleop;
            case "FlywheelHighSpeed":
                return cmdFlywheelHigh;
        }

        System.out.println("Invalid command name. See RobotContainer.java");
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
