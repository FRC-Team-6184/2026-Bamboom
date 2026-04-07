package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class IntakePivotUpCommand extends Command {

    private final IntakeSubsys intake;
    // private final Timer timer = new Timer();

    public IntakePivotUpCommand(IntakeSubsys intake) {
        super();
        this.intake = intake;
        this.addRequirements(intake);
    }

    @Override
    public void initialize() {
        // timer.start();
        intake.pivotUp();
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
