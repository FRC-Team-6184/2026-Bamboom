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
    private final int LED_LENGTH = RobotMap.DigitalValues.LED_LENGTH; // currently 31

    private AddressableLED leds = new AddressableLED(5);
    private AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(LED_LENGTH);

    // Patterns
    private LEDPattern rainbow = LEDPattern.rainbow(255, 150).scrollAtRelativeSpeed(Percent.per(Seconds).of(50)).atBrightness(Percent.of(100));
    private LEDPattern autonomous = LEDPattern.solid(Color.kWhite).breathe(Second.of(.25));
    private LEDPattern xFormation = LEDPattern.solid(Color.kDarkRed).breathe(Second.of(0.25));

    private LEDPattern currentPattern = autonomous;

    public LEDSubsys() {
        super();
        leds.setLength(LED_LENGTH);
        leds.start();
    }

    // Method from inherited Subsystem class which offloads periodic logic from being in a Command.
    @Override
    public void periodic() {
        currentPattern.applyTo(ledBuffer);
        leds.setData(ledBuffer);
    }

    public void setXFormationPattern() {
        currentPattern = xFormation;
    }

    public void setRainbowPattern() {
        currentPattern = rainbow;
    }

    public void setAutonomousPattern() {
        currentPattern = autonomous;
    }
}
