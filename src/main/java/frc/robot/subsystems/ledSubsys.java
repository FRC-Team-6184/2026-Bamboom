package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import com.ctre.phoenix6.mechanisms.swerve.utility.LegacyPhoenixPIDController;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class LEDSubsys extends SubsystemBase {
    private final AddressableLED leds;
    private final AddressableLEDBuffer ledBuffer;

    // Patterns
    private final LEDPattern kDefault;
    private final LEDPattern kAutonomous;
    private final LEDPattern kTeleop;
    private final LEDPattern kXFormation;


    private final LEDPattern kBrownout;
    private final LEDPattern kLowVoltage;
    private LEDPattern currentPattern;

    public LEDSubsys() {
        super();
        leds = new AddressableLED(RobotMap.LEDs.LED_PORT);
        ledBuffer = new AddressableLEDBuffer(RobotMap.LEDs.LED_LENGTH);

        // Patterns
        kDefault = LEDPattern.solid(Color.kGreen); // Green if working fine
        kAutonomous = LEDPattern.solid(Color.kYellow);
        kTeleop = LEDPattern.solid(Color.kGreen);
        kXFormation = LEDPattern.solid(Color.kBlack); // Placeholder Color

        kBrownout = LEDPattern.solid(Color.kBrown);
        kLowVoltage = LEDPattern.solid(Color.kBrown);
        currentPattern = kAutonomous;

        // Start
        leds.setLength(RobotMap.LEDs.LED_LENGTH);
        leds.start();
    }

    // Method from inherited Subsystem class which offloads periodic logic from being in a Command.
    @Override
    public void periodic() {
        currentPattern.applyTo(ledBuffer);
        leds.setData(ledBuffer);
    }

    // Make this method use Enums
    public void setLEDPattern(String LEDPatternName) {
        switch (LEDPatternName) {
            case "Teleop":
                currentPattern = kTeleop;
                break;
            case "Autonomous":
                currentPattern = kAutonomous;
                break;
            case "Default":
                currentPattern = kDefault;
                break;
            case "XFormation":
                currentPattern = kXFormation;
                break;
            case "Brownout":
                currentPattern = kBrownout;
                break;
            case "LowVoltage":
                currentPattern = kLowVoltage;
                break;
        }

    }
}
