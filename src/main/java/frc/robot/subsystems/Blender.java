package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.RobotMap.MotorControllers;
import frc.robot.RobotMap.Controller;

public class Blender extends SubsystemBase {
    private final TalonFX blender = MotorControllers.BLENDER_MOTOR;
    private final CommandXboxController XBOX = Controller.XBOX;

    public Blender() {
        super();
    }
    
    public Command teleopBlender() {
        return run(() -> {
            
            
        });
    }
}
