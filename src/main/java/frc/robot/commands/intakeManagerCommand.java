package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class intakeManagerCommand extends Command {

    private final IntakeSubsys intake;
    private final Timer unjamTimer = new Timer();
    private final Timer timeStalled = new Timer();
    private boolean unjamming = false;

    public intakeManagerCommand(IntakeSubsys intake) {
        intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {

        unjamming = false;

        unjamTimer.stop();
        unjamTimer.reset();

        timeStalled.stop();
        timeStalled.reset();

    }

    @Override
    public void execute() {

        if (!unjamming) {

            intake.setIntakeSpeed(-0.55);

            if (intake.getVelocity() > 2.0) { 
                timeStalled.restart();
            }

            if (timeStalled.hasElapsed(1)) {
                unjamTimer.reset();
                unjamming = true
            }


        } else {

            intake.setIntakeSpeed(0.55);

            if (unjamTimer.hasElapsed(1)) {
                unjamming = false;
                unjamTimer.stop();

            }

        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }

}