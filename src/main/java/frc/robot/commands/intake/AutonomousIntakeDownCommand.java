package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AutonomousSubsys;
import frc.robot.subsystems.IntakeSubsys;

public class AutonomousIntakeDownCommand extends Command {
    IntakeSubsys intake;
    boolean finished = false;


    public AutonomousIntakeDownCommand(IntakeSubsys intake) {
        this.intake = intake;

    }

    @Override
    public void initialize() {
        intake.pivotDown();
        // intake.startIntake();
    }

    @Override
    public void execute() {
        if (intake.isSwitchHit()) {
            intake.pivotStop();
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
