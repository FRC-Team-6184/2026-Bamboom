package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.utilities.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.RobotMap.Controller;
import frc.robot.constants.RobotMap.DigitalInputOutput;
import frc.robot.constants.RobotMap.MotorControllers;

// TODO: Blender and intake might be better as one subsystem rather than two separate since neither should be very complicated.
// Evaluate this once they're both implemented and mush them together later if need be.
public class Intake extends SubsystemBase {
    DigitalInput kTopLimitSwitch;
    DigitalInput kBottomLimitSwitch;
    TalonFX kPivotMotor;
    TalonFX kIntakeMotor;
    CommandXboxController kXboxController;

    /** Intake constructor. Perform all initializing regarding related motors here */
    public Intake() {
        super();

        kTopLimitSwitch = DigitalInputOutput.INTAKE_TOP_LIMIT_SWITCH;
        kBottomLimitSwitch = DigitalInputOutput.INTAKE_BOTTOM_LIMIT_SWITCH;
        kPivotMotor = MotorControllers.PIVOT_INTAKE_MOTOR;
        kIntakeMotor = MotorControllers.ACTIVE_INTAKE_MOTOR;
        kXboxController = Controller.XBOX;
    }

    public Command teleopIntake() {
        return run(() -> {
            // TODO: Implement bringing the motor up and down.
            // Unimplemented as of right now due to the fact that the limit switches are not
            // currently on the robot.

            if (!kTopLimitSwitch.get() && !kBottomLimitSwitch.get()) {
                kPivotMotor.set(MathUtil.clamp(kXboxController.getLeftY(), 0, -0.2));
            }

        });
    }
}
