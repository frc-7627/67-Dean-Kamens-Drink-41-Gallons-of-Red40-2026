package frc.robot.subsystems.swervedrive;

import frc.robot.subsystems.swervedrive.util.Phase;

public class PhaseTracker {
    private Phase phase;

    /**
     * Phase tracking subsystem.
     */
    public PhaseTracker() {
        this.phase = Phase.AUTO;
    }

    /**
     * Gets the current phase.
     * 
     * @return The current phase.
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * Gets whether the phase is currently autonomous.
     * 
     * @return Whether the phase is currently autonomous.
     */
    public boolean isAuto() {
        return phase.equals(Phase.AUTO);
    }
}
