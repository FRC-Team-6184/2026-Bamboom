package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.ledSubsys;

public class XFormationCommand extends Command {
    SwerveSubsys swerve;
    ledSubsys leds;

    public XFormationCommand(SwerveSubsys swerve, ledSubsys leds) {
        super();
        this.swerve = swerve;
        this.leds = leds;
    }

    @Override
    public void initialize() {
        swerve.setCanMove(false);
        leds.setXFormationPattern();
    }

    @Override
    public void execute() {
        swerve.setXFormation();
    }

    @Override
    public void end(boolean interrupted) {
        swerve.setCanMove(true);
        leds.setDefaultPattern();

    }

}
