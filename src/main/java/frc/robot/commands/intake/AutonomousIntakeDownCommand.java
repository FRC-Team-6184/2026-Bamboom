package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class AutonomousIntakeDownCommand extends Command {
    IntakeSubsys intake;
    boolean finished = false;


    public AutonomousIntakeDownCommand(IntakeSubsys intake) {
        this.intake = intake;

    }

    @Override
    public void initialize() {
        finished = false;
        intake.pivotDown();
        intake.slowOuttake();
    }

    @Override
    public void execute() {
        System.out.println(intake.isSwitchHit());
        if (intake.isSwitchHit()) {
            intake.pivotStop();
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }

}
