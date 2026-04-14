package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.RobotMap.Controller;
import frc.robot.subsystems.IntakeSubsys;

public class IntakePivotCommand extends Command {

    // CommandPS5Controller myController = Controller.PS5;
    IntakeSubsys intake;

    public IntakePivotCommand(IntakeSubsys intake) {
        super();
        this.intake = intake;
    }


    @Override
    public void execute() {
        // intake.getPivotMotor().set(myController.getRightY() * 0.1);
        // System.out.println(myController.getRightY() * 0.1);
        intake.getPivotMotor().set(-0.2);
    }

    @Override
    public void end(boolean interrupted) {
        intake.getPivotMotor().set(0.0);
    }

}
