package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

/**
 * The name is ever so unhelpful, but this is an implementation of the intake stuff with the
 * limit switch involved. It should always move down until the limit switch is hit, at which point it stops.
 */
public class IntakePivotLimitSwitchCommand extends Command {
    private IntakeSubsys intake;

    public IntakePivotLimitSwitchCommand(IntakeSubsys intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (!intake.isSwitchHit()) {
            intake.getPivotMotor().set(0.1);
        } else {
            intake.getPivotMotor().set(0.0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.getPivotMotor().set(0.0);
    }
}
