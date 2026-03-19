package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.DigitalInputOutput;
import frc.robot.RobotMap.MotorControllers;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.math.MathUtil;

// TODO: Blender and intake might be better as one subsystem rather than two separate since neither should be very complicated.
// Evaluate this once they're both implemented and mush them together later if need be.
public class IntakeSubsys extends SubsystemBase {
    DigitalInput kTopLimitSwitch;
    DigitalInput kBottomLimitSwitch;
    TalonFX kPivotMotor;
    TalonFX kIntakeMotor;
    CommandXboxController kXboxController;
    CommandPS5Controller myPS5Controler;

    /** Intake constructor. Perform all initializing regarding related motors here */
    public IntakeSubsys() {
        super();

        kTopLimitSwitch = DigitalInputOutput.INTAKE_TOP_LIMIT_SWITCH;
        kBottomLimitSwitch = DigitalInputOutput.INTAKE_BOTTOM_LIMIT_SWITCH;
        kPivotMotor = MotorControllers.PIVOT_INTAKE_MOTOR;
        kIntakeMotor = MotorControllers.ACTIVE_INTAKE_MOTOR;
        kXboxController = Controller.XBOX;
        myPS5Controler = Controller.PS5;

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
        kPivotMotor.set(RobotMap.DigitalValues.INTAKE_PIVOT);
    }

    public void pivotStop() {
        kPivotMotor.set(0);
    }

    public void pivotUp() {
        kPivotMotor.set(-RobotMap.DigitalValues.INTAKE_PIVOT);
    }

    public void startIntake() {
        kIntakeMotor.set(-0.55);
    }

    public void stopIntake() {
        kIntakeMotor.set(0.0);
    }

    public void purgeIntake() {
        kIntakeMotor.set(0.55);
    }
}
