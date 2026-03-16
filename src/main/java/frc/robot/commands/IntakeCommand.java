package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class IntakeCommand extends Command {
    private final IntakeSubsys intake;


    public IntakeCommand(IntakeSubsys intake) {
        super();
        this.intake = intake;
    }

    @Override
    public void initialize() {
        intake.startIntake();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }

}
