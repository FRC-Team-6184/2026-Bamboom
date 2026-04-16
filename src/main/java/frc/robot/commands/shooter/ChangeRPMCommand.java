package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;

public class ChangeRPMCommand extends Command {
    ShooterSubsys shooter;
    boolean increase;

    public ChangeRPMCommand(ShooterSubsys shooter, boolean increase) {
        super();
        this.shooter = shooter;
        this.increase = increase;
    }

    @Override
    public void initialize() {
        if (increase) {
            shooter.increaseFlywheelRPM();
        } else {
            shooter.decreaseFlywheelRPM();
        }
    }

}
