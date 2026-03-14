package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.IntegerEntry;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.MotorControllers;
import frc.robot.utilities.MathUtil;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ShooterSubsys extends SubsystemBase {
    private final TalonFX bottomMotor = MotorControllers.BOTTOM_SHOOTER_WHEEL;
    private final TalonFX topMotor = MotorControllers.TOP_SHOOTER_WHEEL;
    private final TalonFX blenderMotor = MotorControllers.BLENDER_MOTOR; //NOTE: usually runs at -0.5
    private final CommandXboxController controller = Controller.XBOX;
    private NetworkTable network = RobotMap.SoftwareObjects.networkTableInstance.getTable("Shooter");
    private DoubleEntry shooterRPMEntry = network.getDoubleTopic("ShooterRPM Actual").getEntry(0);
    private DoubleEntry shooterRPMDestEntry = network.getDoubleTopic("ShooterRPM Dest.").getEntry(0);
    private DoubleEntry bottomRPMEntry = network.getDoubleTopic("BottomRPM Actual").getEntry(0);
    private DoubleEntry bottomRPMDestEntry = network.getDoubleTopic("BottomRPM Dest").getEntry(0);

    /**
     * Units are in RPS, Rotations Per Second, rather than RPM due to how I recorded the data used in FeedForward
     * <p>Since you're probably used to RPM, Rotations Per Minute, divide the value by 60 before putting it in.
     */
    private VelocityVoltage topMotorSpeedRequest = new VelocityVoltage(0);
    private VelocityVoltage bottomMotorSpeedRequest = new VelocityVoltage(0);


    public ShooterSubsys() {
        super();

        shooterRPMEntry.set(0.0);
        shooterRPMDestEntry.set(0.0);

        bottomRPMEntry.set(0.0);
        bottomRPMDestEntry.set(0.0);

        //Data collected from System Identification (whole complicated thing don't worry about it)
        //These are constants 
        //DO NOT TOUCH PLEASE PLEASE PLEASE
        Slot0Configs topShooterPIDConfig = new Slot0Configs();
        topShooterPIDConfig.kP = 0.1733;
        topShooterPIDConfig.kA = 0.0097241;
        topShooterPIDConfig.kV = 0.11622;
        topShooterPIDConfig.kS = 0.12582;
        topShooterPIDConfig.kD = 0.0; // What SysID gave me
        topMotor.getConfigurator().apply(topShooterPIDConfig);

        Slot0Configs bottomShooterPIDConfig = new Slot0Configs();
        bottomShooterPIDConfig.kP = 0.13694;
        bottomShooterPIDConfig.kA = 0.0019461;
        bottomShooterPIDConfig.kV = 0.11021;
        bottomShooterPIDConfig.kS = 0.027235;
        bottomShooterPIDConfig.kD = 0.0; //What SysID gave me
        bottomMotor.getConfigurator().apply(bottomShooterPIDConfig);

    }

    @Override
    public void periodic() {

    }

    public Command testShoot() {
        return run(() -> {
            controller.x().onTrue(run(() -> {
                topMotor.set(.3);
            }));

            controller.a().onTrue(run(() -> {
                bottomMotor.set(.2);
            }));
        });
    }

    public void shooterOn() {
        topMotor.setControl(topMotorSpeedRequest.withVelocity(RobotMap.DigitalValues.SHOOTER_TOP_RPS));
    }

    public void shooterOn(double rotationsPerSecond) {
        topMotor.setControl(topMotorSpeedRequest.withVelocity(rotationsPerSecond));
    }

    public void shooterOff() {
        topMotor.setControl(topMotorSpeedRequest.withVelocity(0));
    }

    public void bottomOn() {
        bottomMotor.setControl(bottomMotorSpeedRequest.withVelocity(RobotMap.DigitalValues.SHOOTER_BOT_RPS));
    }

    public void bottomOn(double rotationsPerSecond) {
        bottomMotor.setControl(bottomMotorSpeedRequest.withVelocity(rotationsPerSecond));
    }

    public void bottomOff() {
        bottomMotor.set(0);
    }

    public void blenderOn() {
        blenderMotor.set(-0.5);
    }

    public void blenderOn(double power) {
        blenderMotor.set(MathUtil.clamp(power, 1.0, -1.0));
    }

    public void blenderOff() {
        blenderMotor.set(0.0);
    }

}
