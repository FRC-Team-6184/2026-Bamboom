package frc.robot.commands.shooter;

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
        shooter.setFlywheelRPMDest(2700 / 60.0); //That's it! Quick and simple :)
        this.cancel();
    }
}
