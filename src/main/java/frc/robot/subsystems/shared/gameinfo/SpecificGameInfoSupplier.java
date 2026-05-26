package frc.robot.subsystems.shared.gameinfo;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * Interface for supplying specific details during the game. Contains
 * information about the phase of
 * the game and the status of the hub.
 */
public interface SpecificGameInfoSupplier {
    public static enum Phase {
        AUTO, TELEOP, ENDGAME;
    }

    /**
     * @return the current phase.
     */
    Phase getPhase();

    void teleopInit();
}
