package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.RobotMap;

//TODO: Blender and intake might be better as one subsystem rather than two separate since neither should be very complicated.
// Evaluate this once they're both implemented and mush them together later if need be.
public class Intake extends SubsystemBase {
    DigitalInput topLimitSwitch = RobotMap.DigitalInputOutput.INTAKE_TOP_LIMIT_SWITCH;
    DigitalInput bottomLimitSwitch = RobotMap.DigitalInputOutput.INTAKE_BOTTOM_LIMIT_SWITCH;
    TalonFX upAndDownMotor = RobotMap.MotorControllers.UPANDDOWN_INTAKE_MOTOR;
    TalonFX activeMotor = RobotMap.MotorControllers.ACTIVE_INTAKE_MOTOR;


    public Intake() {
        super();
    }

    public Command teleopIntake() {
        return run(() -> {
            //TODO: Implement bringing the motor up and down.
            //Unimplemented as of right now due to the fact that the limit switches are not currently on the robot.
        });
    }


}
