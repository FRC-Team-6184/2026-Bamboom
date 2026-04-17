package frc.robot.commands.flywheel;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotContainer;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.subsystems.LEDSubsys;
import frc.robot.subsystems.ShooterSubsys;

public class FlywheelHighSpeedCommand extends Command {
    // Blender subsystem, and maybe other crucial things.
    private final ShooterSubsys kShooterSubsystem;
    private final LEDSubsys leds;

    public FlywheelHighSpeedCommand(ShooterSubsys shooter, LEDSubsys leds) {
        super();
        this.kShooterSubsystem = shooter;
        super.addRequirements(shooter);
        this.leds = leds;
    }

    @Override
    public void initialize() {
        RobotContainer.setHighSpeed(true);
        // CommandScheduler.getInstance().cancel(RobotContainer.cmdFlywheelLow);
        leds.setLEDPattern("FlywheelOn");
    }

    @Override
    public void execute() {
        kShooterSubsystem.shooterOn(DigitalValues.SHOOTER_HIGH_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        kShooterSubsystem.shooterOff();
        RobotContainer.setHighSpeed(false);
        leds.setLEDPattern("FlywheelOff");
    }

}
