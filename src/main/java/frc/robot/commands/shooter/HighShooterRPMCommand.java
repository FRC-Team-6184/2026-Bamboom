package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.subsystems.ShooterSubsys;

public class HighShooterRPMCommand extends Command {
    private final ShooterSubsys shooter;

    public HighShooterRPMCommand(ShooterSubsys shooter) {
        super();
        this.shooter = shooter;
        this.addRequirements(shooter);
    }

    @Override
    public void initialize() {
        // shooter.setRPMDest(DigitalValues.SHOOTER_HIGH_SPEED); //That's it! Quick and simple :)
        this.cancel();
    }
}
