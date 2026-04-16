package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class AutonomousStartIntakeCommand extends Command {
    IntakeSubsys intake;
    boolean finished = false;


    public AutonomousStartIntakeCommand(IntakeSubsys intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        intake.startIntake();
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
