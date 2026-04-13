package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.RobotMap.MotorControllers;
import frc.robot.RobotMap.SoftwareObjects;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;

public class IntakeSubsys extends SubsystemBase {
    DigitalInput kTopLimitSwitch;
    DigitalInput kBottomLimitSwitch;
    TalonFX kPivotMotor;
    TalonFX kIntakeMotor;
    CommandXboxController kXboxController;
    CommandPS5Controller myPS5Controler;

    VelocityVoltage intakeMotorSpeedRequest = new VelocityVoltage(0.0);

    double intakeRPMDest = -10; //TODO: placeholder value, replace with real one;
    DoubleEntry intakeRPMActual = SoftwareObjects.networkTableInstance.getDoubleTopic("IntakeRPM Actual").getEntry(0);

    /** Intake constructor. Perform all initializing regarding related motors here */
    public IntakeSubsys() {
        super();

        kPivotMotor = MotorControllers.PIVOT_INTAKE_MOTOR;
        kIntakeMotor = MotorControllers.ACTIVE_INTAKE_MOTOR;
        // kXboxController = Controller.XBOX;
        // myPS5Controler = Controller.PS5;

        //This isn't entirely correct, kS was simply really hard to measure properly. Assuming this value.
        //DO NOT TOUCH THIS, THESE ARE CONSTANTS I GOT FROM DATA, PLEASE NO TOUCHY
        Slot0Configs intakeMotorPIDConfigs = new Slot0Configs();
        intakeMotorPIDConfigs.kS = 0.0065; //assuming this value is close enough
        intakeMotorPIDConfigs.kA = 0.022339;
        intakeMotorPIDConfigs.kP = 0.14536;
        intakeMotorPIDConfigs.kV = 0.13043;
        intakeMotorPIDConfigs.kD = 0.0; //Just in case the default is not 0
        kIntakeMotor.getConfigurator().apply(intakeMotorPIDConfigs);

        intakeRPMActual.set(0.0);
    }

    @Override
    public void periodic() {
        intakeRPMActual.set(kIntakeMotor.getVelocity().getValueAsDouble() * 60.0);
    }

    // if (kXboxController.getLeftTriggerAxis() > (RobotMap.DigitalValues.CONTROLLER_DEADZONE * 2)) {
    // kIntakeMotor.set(-0.3);
    // System.out.println("REEEE");
    // } else {
    // kIntakeMotor.set(0.0);
    // }
    // });
    // }

    public void pivotDown() {
        kPivotMotor.set(DigitalValues.INTAKE_PIVOT);
    }

    public void pivotStop() {
        kPivotMotor.set(0);
    }

    public void pivotUp() {
        kPivotMotor.set(-0.75 * DigitalValues.INTAKE_PIVOT);
    }

    public void startIntake() {
        kIntakeMotor.setControl(intakeMotorSpeedRequest.withVelocity(intakeRPMDest));
    }

    public void stopIntake() {
        kIntakeMotor.set(0.0);
    }

    public void purgeIntake() {
        kIntakeMotor.set(0.45);
    }

    public TalonFX getPivotMotor() {
        return kPivotMotor;
    }

    public void setIntakeSpeed(double speed) {
        kIntakeMotor.set(speed);
    }

    public double getVelocity() {
        return kIntakeMotor.getVelocity().getValueAsDouble();
    }

    public void Outtake() {
        kIntakeMotor.set(.75);
    }

    public void setIntakeRPMDest(double rps) {
        intakeRPMDest = rps;
    }

}
