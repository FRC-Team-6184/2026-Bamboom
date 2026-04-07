package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap.Gyro;

public class ResetGyroCommand extends Command {

    public ResetGyroCommand() {
        super();
    }

    @Override
    public void initialize() {
        Gyro.GYRO.reset();
    }

}
