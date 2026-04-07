package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class IntakeManagerCommand extends Command {

    private final IntakeSubsys intake;
    private final Timer unjamTimer = new Timer();
    private final Timer timeStalled = new Timer();
    private final Timer sinceLastUnjam = new Timer();
    private boolean unjamming = false;

    public IntakeManagerCommand(IntakeSubsys intake) {
        this.intake = intake;
        
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        unjamming = false;
        unjamTimer.restart();
        timeStalled.restart();
        sinceLastUnjam.restart();
    }

    @Override
    public void execute() {

        if (!unjamming) {

            intake.startIntake();

            if (intake.getVelocity() <= -2.0) {
                timeStalled.restart();
            }

            if (timeStalled.hasElapsed(0.5)) {
                unjamming = true;
                unjamTimer.restart();
            }

        } else {

            intake.Outtake();

            if (unjamTimer.hasElapsed(3)) {

                System.out.print("me done outtake ");

                unjamming = false;
                timeStalled.restart();
                sinceLastUnjam.restart();
            }

        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }

}

