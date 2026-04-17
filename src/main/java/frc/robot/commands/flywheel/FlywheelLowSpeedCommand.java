package frc.robot.commands.flywheel;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotContainer;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.subsystems.LEDSubsys;
import frc.robot.subsystems.ShooterSubsys;

public class FlywheelLowSpeedCommand extends Command {
    // Blender subsystem, and maybe other crucial things.
    private final ShooterSubsys kShooterSubsystem;
    private final LEDSubsys leds;

    public FlywheelLowSpeedCommand(ShooterSubsys shooter, LEDSubsys leds) {
        super();
        this.kShooterSubsystem = shooter;
        super.addRequirements(shooter);
        this.leds = leds;
    }

    @Override
    public void initialize() {
        RobotContainer.setHighSpeed(false);
        leds.setLEDPattern("FlywheelOn");
        // CommandScheduler.getInstance().cancel(RobotContainer.cmdFlywheelHigh);
    }

    @Override
    public void execute() {
        kShooterSubsystem.shooterOn(DigitalValues.SHOOTER_LOW_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        leds.setLEDPattern("FlywheelOff");
    }

}
