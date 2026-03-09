package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BlenderSubsys;

// Alan requests to spin the top shooter at 30% power, bottom shooter at 20% maybe, and the blender probably at 10% or less.
public class BlenderCommand extends Command {
    // Blender subsystem, and maybe other crucial things.
    private final BlenderSubsys kBlenderSubsystem;

    public BlenderCommand(BlenderSubsys blender) {
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
}
