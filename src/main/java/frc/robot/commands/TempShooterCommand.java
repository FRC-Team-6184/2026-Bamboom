package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;

public class TempShooterCommand extends Command {
    ShooterSubsys shooter;

    public TempShooterCommand(ShooterSubsys shooter) {
        super();
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
        shooter.shooterOn();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.shooterOff();
    }
}
