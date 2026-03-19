package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class IntakeManagerCommand extends Command {
    private final IntakeSubsys m_intake;
    private final Timer m_unjamTimer = new Timer();

    private boolean m_isUnjamming = false;
    private boolean m_isFinished = false;

    public IntakeManagerCommand(IntakeSubsys intake) {
        m_intake = intake;
        addRequirements(m_intake);
    }

    @Override
    public void initialize() {
        m_isUnjamming = false;
        m_isFinished = false;
        m_unjamTimer.stop();
        m_unjamTimer.reset();
    }

    @Override
    public void execute() {
        // 2. Logic for Jamming vs Normal Intaking
        if (!m_isUnjamming) {
            m_intake.setIntakeSpeed(-0.55);

            // Detect Jam: Powered but not moving
            if (m_intake.getVelocity() < 2.0) {
                m_isUnjamming = true;
                m_unjamTimer.restart();
            }
        } else {
            // Reverse for 1.5 seconds to clear the jam
            m_intake.setIntakeSpeed(0.55);

            if (m_unjamTimer.hasElapsed(1.5)) {
                m_isUnjamming = false;
                m_unjamTimer.stop();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.stopIntake();
    }

    @Override
    public boolean isFinished() {
        // Ends if the note is collected OR if the driver toggles it off
        return m_isFinished;
    }
}
