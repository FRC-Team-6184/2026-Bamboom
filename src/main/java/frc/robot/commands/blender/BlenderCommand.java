package frc.robot.commands.blender;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;

/** Command to spin the blender */
public class BlenderCommand extends Command {
    // Blender subsystem, and maybe other crucial things.
    private final ShooterSubsys kShooterSubsys;

    public BlenderCommand(ShooterSubsys shooter) { // Add various other settings here?
        this.kShooterSubsys = shooter;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        kShooterSubsys.blenderOn();
        kShooterSubsys.bottomOn();
    }

    @Override
    public void end(boolean interrupted) {
        kShooterSubsys.blenderOff();
        kShooterSubsys.bottomOff();
    }
}
