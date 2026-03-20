package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.IntakeSubsys;

public class IntakePivotDownCommand extends Command {

    private final IntakeSubsys intake;
    // private final Timer timer = new Timer();

    public IntakePivotDownCommand(IntakeSubsys intake) {
        super();
        this.intake = intake;
        super.addRequirements(intake);
    }

    @Override
    public void initialize() {
        // timer.start();
        intake.pivotDown();
        System.out.println("STARTING PIVOT DOWN");
    }

    @Override
    public void execute() {
        // System.out.println("Waiting for timer" + " | " + timer.get());

        // if (timer.get() >= 0.6) {
        // System.out.println("TIMER OVER");
        // super.cancel();
        // CommandScheduler.getInstance().cancel(this);
        // }
    }

    @Override
    public void end(boolean interrupted) {
        intake.pivotStop();
        intake.startIntake();
        // timer.stop();
        // timer.reset();
    }
}
