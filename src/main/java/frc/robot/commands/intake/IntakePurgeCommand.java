package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class IntakePurgeCommand extends Command {
    IntakeSubsys intake;

    public IntakePurgeCommand(IntakeSubsys intake) {
        super();
        this.intake = intake;
        super.addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.purgeIntake();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }

}
