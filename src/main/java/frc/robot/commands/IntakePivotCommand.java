package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.RobotMap.Controller;
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.utilities.MathUtil;

public class IntakePivotCommand extends Command {

    CommandPS5Controller myController = Controller.PS5;
    IntakeSubsys intake;

    public IntakePivotCommand(IntakeSubsys intake) {
        super();
        this.intake = intake;
    }


    @Override
    public void execute() {
        intake.getPivotMotor().set(MathUtil.clamp(myController.getRightY() * .2, 0, -0.2));
    }

}
