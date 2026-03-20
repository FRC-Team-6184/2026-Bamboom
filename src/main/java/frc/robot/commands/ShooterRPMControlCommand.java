package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.subsystems.ShooterSubsys;

public class ShooterRPMControlCommand extends Command {
    ShooterSubsys shooter;

    public ShooterRPMControlCommand(ShooterSubsys shooter) {
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
        shooter.setRPMDest(DigitalValues.SHOOTER_HIGH_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setRPMDest(DigitalValues.SHOOTER_LOW_SPEED);
    }


}
