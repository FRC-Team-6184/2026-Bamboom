package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.GameController;
import frc.robot.utils.Hardware;
import frc.robot.utils.Utilities;

public class Shooter extends SubsystemBase {
    private TalonFX bottomMotor = Hardware.bottomShooterMotor;
    private TalonFX topMotor = Hardware.topShooterMotor;
    private GameController controller = Hardware.controller;
    private NetworkTable network = Hardware.networkTableInstance.getTable("Shooter");
    private DoubleEntry shooterRPMEntry = network.getDoubleTopic("ShooterRPM Actual").getEntry(0);
    private DoubleEntry shooterRPMDestEntry = network.getDoubleTopic("ShooterRPM Dest.").getEntry(0);
    private DoubleEntry bottomRPMEntry = network.getDoubleTopic("BottomRPM Actual").getEntry(0);
    private DoubleEntry bottomRPMDestEntry = network.getDoubleTopic("BottomRPM Dest").getEntry(0);

    // kv is in V/RPS, calculated using the data from https://www.reca.lc/motors
    // (their kv is in RPM/V, reciprocal of desired and not in units per second)
    // private SimpleMotorFeedforward test = new SimpleMotorFeedforward(-1,
    // 8.9133333333333333333333333333333);

    public Shooter() {
        super();

        shooterRPMEntry.set(0.0);
        shooterRPMDestEntry.set(0.0);

        bottomRPMEntry.set(0.0);
        bottomRPMDestEntry.set(0.0);
    }

    /**
     * Put into scheduler upon start of teleop, needs to be run periodically.
     * 
     * @return Command regarding teleop shooter behavior
     */
    public Command teleopShoot() {
        return run(() -> {
            // Slot0Configs pidConfig = new Slot0Configs();
            // pidConfig.kA = 0;
            // topMotor.getConfigurator().apply(pidConfig);

            // TODO: make these PID controlled running at a set RPM instead of being -1.0 to
            // 1.0
            double topPower = shooterRPMDestEntry.get();
            topPower = Utilities.clamp(topPower, 1.0, -1.0);
            double bottomPower = shooterRPMDestEntry.get();
            bottomPower = Utilities.clamp(bottomPower, 1.0, -1.0);
            if (controller.getRightTrigger() > (Hardware.CONTROLLER_DEADZONE * 2)) {
                // TODO: run motors according to dashboard

                topMotor.set(topPower);
                bottomMotor.set(bottomPower);
            } else {
                topMotor.set(0);
                bottomMotor.set(0);
            }

            shooterRPMEntry.set(topMotor.getVelocity().getValue().in(Units.RPM));
            bottomRPMEntry.set(bottomMotor.getVelocity().getValue().in(Units.RPM));

        });
    }

}
