package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ShooterSubsys;

/**
 * A command that modifies the shooter RPM by a specific offset.
 * Designed to be bound to D-pad (POV) buttons.
 */
public class ShooterRPMControlCommand extends InstantCommand {

    private final ShooterSubsys shooter;
    private final double increment;



    /**
     * @param shooter   The shooter subsystem.
     * @param increment The amount to add to the current RPM (use negative for subtraction).
     */

    public ShooterRPMControlCommand(ShooterSubsys shooter, double increment) {
        this.shooter = shooter;
        this.increment = increment;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {

        // Calculate new speed based on the Subsystem's current memory
        double newSpeed = shooter.getCurrentRPMSetpoint() + increment;
        shooter.setRPM(newSpeed);

    }
}
