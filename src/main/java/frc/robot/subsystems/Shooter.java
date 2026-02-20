package frc.robot.subsystems;

import java.security.KeyStore.Entry;
import java.time.format.TextStyle;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.units.AngularVelocityUnit;
import edu.wpi.first.units.Unit;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.utils.GameController;
import frc.robot.utils.Hardware;

public class Shooter extends SubsystemBase {
    private TalonFX bottomMotor = Hardware.bottomShooterMotor;
    private TalonFX topMotor = Hardware.topShooterMotor;
    private GameController controller = Hardware.controller;
    private NetworkTable network = Hardware.networkTableInstance.getTable("Shooter");
    private DoubleEntry shooterRPMEntry = network.getDoubleTopic("ShooterRPM Actual").getEntry(0);
    private DoubleEntry shooterRPMDestEntry = network.getDoubleTopic("ShooterRPM Dest.").getEntry(0);
    private DoubleEntry bottomRPMEntry = network.getDoubleTopic("BottomRPM Actual").getEntry(0);
    private DoubleEntry bottomRPMDestEntry = network.getDoubleTopic("BottomRPM Dest").getEntry(0);

    // SysIdRoutine.Config conf = new SysIdRoutine.Config(); // using default config
    // SysIdRoutine.Mechanism mech = new SysIdRoutine.Mechanism(topMotor::setVoltage, log -> {
    //     log
    // }, this)
    // SysIdRoutine identification = new SysIdRoutine(topMotor::setVoltage, );

    
    //kv is in V/RPS, calculated using the data from https://www.reca.lc/motors (their kv is in RPM/V, reciprocal of desired and not in units per second)
    // private SimpleMotorFeedforward test = new SimpleMotorFeedforward(-1, 8.9133333333333333333333333333333);

    public Shooter() {
        super();

    }

    public Command teleopShoot() {
        return run(() -> {
            Slot0Configs pidConfig = new Slot0Configs();
            pidConfig.kA = 0;
            topMotor.getConfigurator().apply(pidConfig);

            if (controller.getRightTrigger() > (Hardware.CONTROLLER_DEADZONE * 2)) {
                // TODO: run motors according to dashboard

                topMotor.set(1);
                bottomMotor.set(-1);
            } else {
                topMotor.set(0);
                bottomMotor.set(0);
            }

            shooterRPMEntry.set(topMotor.getVelocity().getValue().in(Units.RPM));
            bottomRPMEntry.set(bottomMotor.getVelocity().getValue().in(Units.RPM));


        });
    }

}
