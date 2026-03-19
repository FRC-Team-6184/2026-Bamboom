package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class IntakeSubsys extends SubsystemBase {
    private final TalonFX kIntakeMotor = RobotMap.MotorControllers.ACTIVE_INTAKE_MOTOR;
    private final DigitalInput kTopLimitSwitch = RobotMap.DigitalInputOutput.INTAKE_TOP_LIMIT_SWITCH;

    public void setIntakeSpeed(double speed) {
        kIntakeMotor.set(speed);
    }

    public void stopIntake() {
        kIntakeMotor.set(0);
    }

    public double getVelocity() {
        return kIntakeMotor.getVelocity().getValueAsDouble();
    }

    public boolean isNoteCollected() {
        // Assuming the limit switch returns true when the note hits it
        return kTopLimitSwitch.get();
    }
}