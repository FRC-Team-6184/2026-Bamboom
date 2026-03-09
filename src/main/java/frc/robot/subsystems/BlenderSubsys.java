package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.RobotMap.MotorControllers;

// TODO: Make this class structured like the Intake subsystem class, it looks nicer over there.
public class BlenderSubsys extends SubsystemBase {
    private TalonFX blender = MotorControllers.BLENDER_MOTOR;
    private CommandXboxController XBOX = Controller.XBOX;

    public BlenderSubsys() {
        super();


    }

    public Command teleopBlender() {
        return run(() -> {
            blender.set(0.1);
        });
    }

    public Command testBlender() {
        return run(() -> {
            blender.set(MathUtil.clamp(XBOX.getLeftTriggerAxis(), 0, 0.2));
        });
    }
}
