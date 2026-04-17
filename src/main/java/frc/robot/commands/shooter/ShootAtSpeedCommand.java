package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;

public class ShootAtSpeedCommand extends Command {
    ShooterSubsys shooter;
    double rps;

    public ShootAtSpeedCommand(ShooterSubsys shooter, double rps) {
        this.shooter = shooter;
        this.rps = rps;
    }

    @Override
    public void initialize() {
        shooter.shooterOn(rps);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.shooterOff();
    }

}
