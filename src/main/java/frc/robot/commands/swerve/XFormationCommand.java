package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.other.LEDXFormationCommand;
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.LEDSubsys;

public class XFormationCommand extends Command {
    SwerveSubsys swerve;
    LEDSubsys leds;
    LEDXFormationCommand XFormationCommand;

    public XFormationCommand(SwerveSubsys swerve, LEDSubsys leds) {
        super();

        XFormationCommand = new LEDXFormationCommand();
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
        leds.setRainbowPattern();

    }

}
