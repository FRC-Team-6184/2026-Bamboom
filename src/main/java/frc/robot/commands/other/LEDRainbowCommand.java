package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ledSubsys;

public class LEDRainbowCommand extends Command {
    private final ledSubsys kLEDSubsystem;

    public LEDRainbowCommand() {
        kLEDSubsystem = new ledSubsys();
    }

    @Override
    public void initialize() {
        kLEDSubsystem.setRainbowPattern();
        kLEDSubsystem.configureLEDs();
        kLEDSubsystem.runLED();
    }

    @Override
    public void execute() {

    }
}
