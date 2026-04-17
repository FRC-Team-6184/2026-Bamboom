package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ledSubsys;

public class LEDXFormationCommand extends Command {
    private final ledSubsys kLEDSubsystem;

    public LEDXFormationCommand() {
        kLEDSubsystem = new ledSubsys();
    }

    @Override
    public void initialize() {
        kLEDSubsystem.setXFormationPattern();
        kLEDSubsystem.runLED();
    }

    public void execute() {

    }
}
