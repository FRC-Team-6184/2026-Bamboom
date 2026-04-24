package frc.robot.commands.flywheel;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.subsystems.ShooterSubsys;

public class FlywheelHighSpeedCommand extends Command {
    // Blender subsystem, and maybe other crucial things.
    private final ShooterSubsys kShooterSubsystem;

    public FlywheelHighSpeedCommand(ShooterSubsys shooter) {
        super();
        this.kShooterSubsystem = shooter;
        super.addRequirements(shooter);
    }

    @Override
    public void initialize() {
        RobotContainer.setHighSpeed(true);
        // CommandScheduler.getInstance().cancel(RobotContainer.cmdFlywheelLow);
    }

    @Override
    public void execute() {
        kShooterSubsystem.shooterOn(DigitalValues.SHOOTER_HIGH_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        kShooterSubsystem.shooterOff();
        RobotContainer.setHighSpeed(false);
    }

}
