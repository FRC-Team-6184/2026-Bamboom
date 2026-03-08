package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.RobotMap.Controller;
import frc.robot.constants.RobotMap.DigitalValues;
import frc.robot.constants.RobotMap.MotorControllers;

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
