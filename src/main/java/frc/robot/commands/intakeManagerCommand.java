package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class intakeManagerCommand extends Command {

    private final IntakeSubsys intake;
    private final Timer unjamTimer = new Timer();
    private final Timer timeStalled = new Timer();
    private final Timer sinceLastUnjam = new Timer();
    private boolean unjamming = false;

    public intakeManagerCommand(IntakeSubsys intake) {

        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() {

        unjamming = false;

        unjamTimer.stop();
        unjamTimer.restart();

        timeStalled.stop();
        timeStalled.restart();

        sinceLastUnjam.stop();
        sinceLastUnjam.restart();



    }

    @Override
    public void execute() {

        System.out.print("i loop");

        System.out.print("vel" + intake.getVelocity());

        if (!unjamming) {

            intake.startIntake();

            if (intake.getVelocity() < -2) {
                timeStalled.restart();
            }

            if (timeStalled.hasElapsed(.25) && sinceLastUnjam.hasElapsed(3)) {

                System.out.print("test");

                unjamTimer.restart();
                unjamming = true;
            }


        } else {

            intake.Outtake();

            if (unjamTimer.hasElapsed(3)) {

                System.out.print("me done outtake ");

                unjamming = false;
                unjamTimer.restart();

                sinceLastUnjam.restart();

            }

        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }

}
