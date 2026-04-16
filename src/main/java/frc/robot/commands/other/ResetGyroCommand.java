package frc.robot.commands.other;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap.Gyro;

public class ResetGyroCommand extends Command {

    public ResetGyroCommand() {
        super();
    }

    boolean finished = false;

    @Override
    public void initialize() {
        Gyro.GYRO.reset();
        System.out.println(Gyro.GYRO.getRotation2d());
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
