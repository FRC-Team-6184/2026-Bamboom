package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Seconds;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ledSubsys extends SubsystemBase {
    private final int LED_LENGTH = 32;

    private AddressableLED leds = new AddressableLED(3);
    private AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(LED_LENGTH);
    private LEDPattern rainbow = LEDPattern.rainbow(255, 150).scrollAtRelativeSpeed(Percent.per(Seconds).of(50)).atBrightness(Percent.of(100));



    public ledSubsys() {
        super();

        leds.setLength(32);
        leds.start();
    }

    @Override
    public void periodic() {
        rainbow.applyTo(ledBuffer);

        leds.setData(ledBuffer);
    }
}
