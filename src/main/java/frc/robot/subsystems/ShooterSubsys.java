package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;

import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.RobotMap.MotorControllers;
import frc.robot.RobotMap.SoftwareObjects;
import frc.robot.utilities.MathUtil;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ShooterSubsys extends SubsystemBase {
    private final TalonFX bottomMotor = MotorControllers.BOTTOM_SHOOTER_WHEEL;
    private final TalonFX topMotor = MotorControllers.TOP_SHOOTER_WHEEL;
    private final TalonFX blenderMotor = MotorControllers.BLENDER_MOTOR; //NOTE: usually runs at -0.5
    private final CommandXboxController controller = Controller.XBOX;
    private NetworkTable network = SoftwareObjects.networkTableInstance.getTable("Shooter");
    private DoubleEntry shooterRPMEntry = network.getDoubleTopic("ShooterRPM Actual").getEntry(0);
    private DoubleEntry shooterRPMTargetEntry = network.getDoubleTopic("ShooterRPM Target").getEntry(0);
    private DoubleEntry bottomRPMEntry = network.getDoubleTopic("BottomRPM Actual").getEntry(0);

    private NetworkTable pidTable = network.getSubTable("TopPID");
    private DoubleEntry ntKP = pidTable.getDoubleTopic("kP").getEntry(0.1733);
    private DoubleEntry ntKD = pidTable.getDoubleTopic("kD").getEntry(0.0);
    private DoubleEntry ntKV = pidTable.getDoubleTopic("kV").getEntry(0.11622);
    private DoubleEntry ntKS = pidTable.getDoubleTopic("kS").getEntry(0.12582);
    private DoubleEntry ntKA = pidTable.getDoubleTopic("kA").getEntry(0.0097241);

    private double m_kP = 0.1733, m_kD = 0.0, m_kV = 0.11622, m_kS = 0.12582, m_kA = 0.0097241;

    private double shooterRPMDest = DigitalValues.SHOOTER_HIGH_SPEED;
    private double m_targetRPM = DigitalValues.SHOOTER_HIGH_SPEED;
    private double kickerRPMDest = -4500 / 60.0; //placeholder values
    private double blenderRPMDest = 1.25 * shooterRPMDest;

    /**
     * Units are in RPS, Rotations Per Second, rather than RPM due to how I recorded the data used in FeedForward
     * <p>Since you're probably used to RPM, Rotations Per Minute, divide the value by 60 before putting it in.
     */
    private VelocityVoltage topMotorSpeedRequest = new VelocityVoltage(0);
    private VelocityVoltage bottomMotorSpeedRequest = new VelocityVoltage(0);
    private VelocityVoltage blenderMotorSpeedRequest = new VelocityVoltage(0);


    public ShooterSubsys() {
        super();

        shooterRPMEntry.set(0.0);
        shooterRPMTargetEntry.set(0.0);
        bottomRPMEntry.set(0.0);
        ntKP.set(m_kP);
        ntKD.set(m_kD);
        ntKV.set(m_kV);
        ntKS.set(m_kS);
        ntKA.set(m_kA);



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
        bottomMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive));

        Slot0Configs blenderPIDConfig = new Slot0Configs();
        blenderPIDConfig.kP = 0.14905;
        blenderPIDConfig.kA = 0.0029793;
        blenderPIDConfig.kV = 0.11111;
        blenderPIDConfig.kS = 0.049802;
        blenderPIDConfig.kD = 0.0; //Still what SysID gave me. This value probably defaults to 0.0, but I don't trust it.
        blenderMotor.getConfigurator().apply(blenderPIDConfig);

    }

    @Override
    public void periodic() {
        shooterRPMEntry.set(topMotor.getVelocity().getValueAsDouble() * 60);
        shooterRPMTargetEntry.set(m_targetRPM * 60);
        bottomRPMEntry.set(bottomMotor.getVelocity().getValueAsDouble() * 60);

        double newKP = ntKP.get(m_kP);
        double newKD = ntKD.get(m_kD);
        double newKV = ntKV.get(m_kV);
        double newKS = ntKS.get(m_kS);
        double newKA = ntKA.get(m_kA);
        if (newKP != m_kP || newKD != m_kD || newKV != m_kV || newKS != m_kS || newKA != m_kA) {
            m_kP = newKP; m_kD = newKD; m_kV = newKV; m_kS = newKS; m_kA = newKA;
            Slot0Configs updated = new Slot0Configs();
            updated.kP = m_kP; updated.kD = m_kD; updated.kV = m_kV; updated.kS = m_kS; updated.kA = m_kA;
            topMotor.getConfigurator().apply(updated);
        }

        blenderRPMDest = 1.125 * shooterRPMDest;
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

    private double m_currentRPMSetpoint = 0;

    public void setRPM(double rpm) {
        m_targetRPM = rpm;
        shooterRPMDest = rpm;
        topMotor.setControl(topMotorSpeedRequest.withVelocity(rpm));
    }



    public double getCurrentRPMSetpoint() {
        return m_targetRPM;
    }

    public void shooterOn() {
        topMotor.setControl(topMotorSpeedRequest.withVelocity(shooterRPMDest));
    }

    public void shooterOn(double rotationsPerSecond) {
        topMotor.setControl(topMotorSpeedRequest.withVelocity(shooterRPMDest));
    }

    public void shooterOff() {
        topMotor.setControl(topMotorSpeedRequest.withVelocity(0));
    }

    public void bottomOn() {
        bottomMotor.setControl(bottomMotorSpeedRequest.withVelocity(-kickerRPMDest));
    }

    public void bottomOn(double rotationsPerSecond) {
        bottomMotor.setControl(bottomMotorSpeedRequest.withVelocity(-rotationsPerSecond));
    }

    public void bottomOff() {
        bottomMotor.set(0);
    }

    public void blenderOn() {
        blenderMotor.setControl(blenderMotorSpeedRequest.withVelocity(-blenderRPMDest));
    }

    public void blenderOn(double power) {
        blenderMotor.set(MathUtil.clamp(-power, 1.0, -1.0));
    }

    public void blenderOff() {
        blenderMotor.set(0);
    }

    public double getRPMDDest() {
        return shooterRPMDest;
    }


    public void setFlywheelRPMDest(double shooterRPMDest) {
        this.shooterRPMDest = shooterRPMDest;
    }

    public void setBlenderRPMDest(double blenderRPMDest) {
        this.blenderRPMDest = blenderRPMDest;
    }

    public void setKickerRPMDest(double kickerRPMDest) {
        this.kickerRPMDest = kickerRPMDest;
    }



}
