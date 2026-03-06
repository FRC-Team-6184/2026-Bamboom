package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.RobotMap.MotorControllers;

/* TODO: 
 * 
 */

public class Blender extends SubsystemBase {
    private final TalonFX blender = MotorControllers.BLENDER_MOTOR;

    public Blender() {
        super();
    }
    
    public Command teleopBlender() {
        return run(() -> {
            blender.set(DigitalValues.SUPER_LOW);
        });
    }
}
