package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.IntegerEntry;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.units.Units;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.MotorControllers;
import frc.robot.utilities.MathUtil;

public class Shooter extends SubsystemBase {
    private final TalonFX bottomMotor = MotorControllers.BOTTOM_SHOOTER_WHEEL;
    private final TalonFX topMotor = MotorControllers.TOP_SHOOTER_WHEEL;
    private final CommandXboxController controller = Controller.XBOX;
    private NetworkTable network = RobotMap.OtherDigitalStuff.networkTableInstance.getTable("Shooter");
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


    public Shooter() {
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
        topShooterPIDConfig.kD = 0.0; //What SysID gave me
        topMotor.getConfigurator().apply(topShooterPIDConfig);

        Slot0Configs bottomShooterPIDConfig = new Slot0Configs();
        topShooterPIDConfig.kP = 0.13694;
        topShooterPIDConfig.kA = 0.0019461;
        topShooterPIDConfig.kV = 0.11021;
        topShooterPIDConfig.kS = 0.027235;
        topShooterPIDConfig.kD = 0.0; //What SysID gave me
        bottomMotor.getConfigurator().apply(bottomShooterPIDConfig);

    }

    @Override
    public void periodic() {

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

            if (controller.getRightTriggerAxis() > (RobotMap.DigitalValues.CONTROLLER_DEADZONE * 2)) {
                // TODO: run motors according to dashboard
                topMotor.setControl(topMotorSpeedRequest.withVelocity(MathUtil.clamp(shooterRPMDestEntry.get(0.0) / 60.0, 6000.0, -6000.0)));
                // System.out.println(topMotorRPM);
                bottomMotor.set(-0.5);
                // bottomMotor.setControl(bottomMotorSpeedRequest.withVelocity(bottomRPMDestEntry.get(0.0)));
            } else {
                topMotor.set(0);
                bottomMotor.set(0);
            }

            shooterRPMEntry.set(topMotorRPM);
            bottomRPMEntry.set(bottomMotorRPM);

        });
    }

}
