package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;

public class ShooterCommand extends Command {
    private final ShooterSubsys shooter;
    private final SwerveSubsys swerve;

    private enum States {
        SEEKING, SHOOTING
    };

    private States currentState = States.SEEKING;

    public ShooterCommand(ShooterSubsys shooter, SwerveSubsys swerve) {
        super();

        this.shooter = shooter;
        this.swerve = swerve;
    }

    @Override
    public void initialize() {
        swerve.setCanMove(false);
        swerve.setCanRotate(false);
    }

    @Override
    public void execute() {
        switch (currentState) {
            case SEEKING:

                break;
            case SHOOTING:

                break;
        }

    }

    @Override
    public void end(boolean interrupted) {
        swerve.setCanMove(true);
        swerve.setCanRotate(true);
    }
}
