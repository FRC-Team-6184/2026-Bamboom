package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.GameController;
import frc.robot.hardware.RobotMap;
import frc.robot.hardware.RobotMap.Controller;
import frc.robot.hardware.RobotMap.MotorControllers;

public class Shooter extends SubsystemBase {
    private final TalonFX bottomMotor = MotorControllers.BOTTOM_SHOOTER_WHEEL;
    private final TalonFX topMotor = MotorControllers.TOP_SHOOTER_WHEEL;
    private final GameController controller = Controller.GAME_CONTROLLER;
    private NetworkTable network = RobotMap.networkTableInstance.getTable("Shooter");
    private DoubleEntry shooterRPMEntry = network.getDoubleTopic("ShooterRPM Actual").getEntry(0);
    private DoubleEntry shooterRPMDestEntry = network.getDoubleTopic("ShooterRPM Dest.").getEntry(0);
    private DoubleEntry bottomRPMEntry = network.getDoubleTopic("BottomRPM Actual").getEntry(0);
    private DoubleEntry bottomRPMDestEntry = network.getDoubleTopic("BottomRPM Dest").getEntry(0);
    private VelocityVoltage voltageRequest = new VelocityVoltage(0);

    public Shooter() {
        super();

        shooterRPMEntry.set(0.0);
        shooterRPMDestEntry.set(0.0);

        bottomRPMEntry.set(0.0);
        bottomRPMDestEntry.set(0.0);

        Slot0Configs topShooterPIDConfig = new Slot0Configs();
        topShooterPIDConfig.kP = 0.1733;
        topShooterPIDConfig.kA = 0.0097241;
        topShooterPIDConfig.kV = 0.11622;
        topShooterPIDConfig.kS = 0.12582;
        topShooterPIDConfig.kD = 0.0; //What SysID gave me
        topMotor.getConfigurator().apply(topShooterPIDConfig);
    }

    /**
     * Put into scheduler upon start of teleop, needs to be run periodically.
     * 
     * @return Command regarding teleop shooter behavior
     */
    public Command teleopShoot() {
        return run(() -> {
            double topMotorRPM = topMotor.getVelocity().getValue().in(Units.RPM);
            double bottomMotorRPM = bottomMotor.getVelocity().getValue().in(Units.RPM);

            if (controller.getRightTrigger() > (RobotMap.Controller.CONTROLLER_DEADZONE * 2)) {
                // TODO: run motors according to dashboard
                topMotor.setControl(voltageRequest.withVelocity(2.0));
                System.out.println(topMotorRPM);
                bottomMotor.set(0.1); //TODO: this isn't running at a proper speed fix later
            } else {
                topMotor.set(0);
                bottomMotor.set(0);
            }

            shooterRPMEntry.set(topMotorRPM);
            bottomRPMEntry.set(bottomMotorRPM);

        });
    }

}
