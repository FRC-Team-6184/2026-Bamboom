package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class IntakePivotDownCommand extends Command {

    private final IntakeSubsys intake;
    // private final Timer timer = new Timer();

    public IntakePivotDownCommand(IntakeSubsys intake) {
        super();
        this.intake = intake;
    }

    @Override
    public void initialize() {
        // timer.start();
        intake.pivotDown();
    }

    @Override
    public void execute() {
        // if(timer.hasElapsed(0.25)) {
        //     end(false);
        //     super.is
        // }
    }

    @Override
    public void end(boolean interrupted) {
        intake.pivotStop();
    }
}
