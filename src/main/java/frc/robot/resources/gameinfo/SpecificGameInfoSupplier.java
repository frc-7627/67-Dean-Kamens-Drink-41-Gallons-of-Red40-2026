package frc.robot.resources.gameinfo;

/**
 * Interface for supplying specific details during the game.
 * Contains information about the phase of the game and the status of the hub.
 */
public interface SpecificGameInfoSupplier {
    public static enum Phase {
        AUTO, TRANSITION, TELEOP_1, TELEOP_2, TELEOP_3, TELEOP_4, ENDGAME;
    }

    Phase getPhase();

    boolean isHubActive();
}
