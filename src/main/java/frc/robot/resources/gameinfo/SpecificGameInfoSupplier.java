package frc.robot.resources.gameinfo;

import frc.robot.resources.Resource;

/**
 * Interface for supplying specific details during the game.
 * Contains information about the phase of the game and the status of the hub.
 */
public interface SpecificGameInfoSupplier extends Resource {
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
}
