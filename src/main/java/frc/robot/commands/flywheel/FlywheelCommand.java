package frc.robot.commands.flywheel;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;

public class FlywheelCommand extends Command {
    // Blender subsystem, and maybe other crucial things.
    private final ShooterSubsys kShooterSubsystem;

    public FlywheelCommand(ShooterSubsys shooter) {
        this.kShooterSubsystem = shooter;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        kShooterSubsystem.shooterOn();
    }

    @Override
    public void end(boolean interrupted) {
        kShooterSubsystem.shooterOff();
    }

}
