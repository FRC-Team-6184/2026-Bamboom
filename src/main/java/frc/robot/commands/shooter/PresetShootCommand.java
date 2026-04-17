package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;

public class PresetShootCommand extends Command {
    private final ShooterSubsys shooter;
    private final double rps;

    /** @param rps target speed in rotations per second (RPM / 60) */
    public PresetShootCommand(ShooterSubsys shooter, double rps) {
        this.shooter = shooter;
        this.rps = rps;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.setRPM(rps);
    }

    @Override
    public void execute() {
        shooter.shooterOn();
        shooter.blenderOn();
        shooter.bottomOn();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.shooterOff();
        shooter.blenderOff();
        shooter.bottomOff();
    }
}
