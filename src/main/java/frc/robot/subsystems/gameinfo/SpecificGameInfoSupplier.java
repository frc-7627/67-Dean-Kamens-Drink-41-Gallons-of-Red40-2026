package frc.robot.subsystems.gameinfo;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * Interface for supplying specific details during the game. Contains information about the phase of
 * the game and the status of the hub.
 */
public interface SpecificGameInfoSupplier {
    public static enum Phase {
        AUTO, TRANSITION, TELEOP_1, TELEOP_2, TELEOP_3, TELEOP_4, ENDGAME;
    }

    /**
     * @return the current phase.
     */
    Phase getPhase();

    /**
     * @return whether the hub is active.
     */
    boolean isHubActive();

    /**
     * @return the hub position.
     */
    Translation2d getHubPosition();
}
