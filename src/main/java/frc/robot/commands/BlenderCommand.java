package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BlenderSubsys;

/** Command to spin the blender */
public class BlenderCommand extends Command {
    // Blender subsystem, and maybe other crucial things.
    private final BlenderSubsys kBlenderSubsystem;

    public BlenderCommand(BlenderSubsys blender) { // Add various other settings here?
        this.kBlenderSubsystem = blender;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    /** Factory to return an initialized BlenderCommand */
    public static final class BlenderCommandFactory {
        public BlenderCommand getBlenderCommand() {
            return new BlenderCommand(new BlenderSubsys());
        }
    }
}
