package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.LEDSubsys;

public class XFormationCommand extends Command {
    SwerveSubsys swerve;
    LEDSubsys leds;

    public XFormationCommand(SwerveSubsys swerve, LEDSubsys leds) {
        super();

        this.swerve = swerve;
        this.leds = leds;
    }

    @Override
    public void initialize() {
        swerve.setCanMove(false);
        leds.setLEDPattern("XFormation");
    }

    @Override
    public void execute() {
        swerve.setXFormation();

        if (RobotController.getBatteryVoltage() < 10) {

        }
    }

    @Override
    public void end(boolean interrupted) {
        swerve.setCanMove(true);
        leds.setLEDPattern("Default");
    }

}
