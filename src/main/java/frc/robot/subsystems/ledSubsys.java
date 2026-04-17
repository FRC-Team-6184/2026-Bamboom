package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import com.ctre.phoenix6.mechanisms.swerve.utility.LegacyPhoenixPIDController;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class LEDSubsys extends SubsystemBase {
    private final AddressableLED leds;
    private final AddressableLEDBuffer ledBuffer;

    // Patterns
    private final LEDPattern kAutonomous;
    private final LEDPattern kTeleop;
    private final LEDPattern kXFormation;

    private final LEDPattern kStuckMotor; // Implement this
    private final LEDPattern kBrownout;
    private final LEDPattern kLowVoltage;
    private LEDPattern currentPattern;

    public LEDSubsys() {
        super();
        leds = new AddressableLED(RobotMap.LEDs.LED_PORT);
        ledBuffer = new AddressableLEDBuffer(RobotMap.LEDs.LED_LENGTH);

        // Command LED Patterns
        kAutonomous = LEDPattern.solid(Color.kYellow);
        kTeleop = LEDPattern.solid(Color.kWhite);
        kXFormation = LEDPattern.solid(Color.kOrange); // Placeholder Color

        // Warning Patterns
        kBrownout = LEDPattern.solid(Color.kBrown);
        kLowVoltage = LEDPattern.solid(Color.kRed).breathe(Seconds.of(.25));
        kStuckMotor = LEDPattern.solid(Color.kRed);



         // Start
        leds.setLength(RobotMap.LEDs.LED_LENGTH);
        leds.start();

    }

    @Override
    public void periodic() {
        if (RobotController.getBatteryVoltage() <= 11) {
            kLowVoltage.applyTo(ledBuffer);
            leds.setData(ledBuffer);
            return;
        }
        if (RobotController.getBatteryVoltage() <= 6.3) { // Change this later so its checking motor brownouts. wont ever reach this low a voltage
            kBrownout.applyTo(ledBuffer);
            leds.setData(ledBuffer);
            return;
        }

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
            case "XFormation":
                currentPattern = kXFormation;
                break;
            case "Brownout":
                currentPattern = kBrownout;
                break;
            case "LowVoltage":
                currentPattern = kLowVoltage;
                break;
            case "StuckMotor":
                currentPattern = kStuckMotor;
                break;
        }

    }
}
