package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.subsystems.ShooterSubsys;

public class LowShooterRPMCommand extends Command {
    private final ShooterSubsys shooter;

    public LowShooterRPMCommand(ShooterSubsys shooter) {
        super();
        this.shooter = shooter;
        super.addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.setRPMDest(DigitalValues.SHOOTER_LOW_SPEED); //That's it! Quick and simple :)
        this.cancel();
    }
}
