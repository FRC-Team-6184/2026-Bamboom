package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.RobotMap.MotorControllers;
/* TODO: 
 * 
 */

public class Blender extends SubsystemBase {
    private final TalonFX blender = MotorControllers.BLENDER_MOTOR;

    // TODO: Move this into RobotMap.java sometime, underneath the category for digital values.
    private enum Speeds {
        
    }

    public Blender() {
        super();
    }
    
    public Command teleopBlender() {
        return run(() -> {
            blender.set(0.5);
        });
    }
}
