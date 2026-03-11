package frc.robot.subsystems.shared.gameinfo;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.controllable.drivebase.Side;

/**
 * Interface for supplying specific details during the game. Contains
 * information about the phase of
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

    void teleopInit();

    /**
     * @return whether the hub is active.
     */
    boolean isHubActive();

    /**
     * @return whether the hub will activate in the next 3 seconds, or is already
     *         active
     */
    boolean willHubActivate();

    /**
     * @return the hub position.
     */
    Translation2d getHubPosition();

    Translation2d getFerryTargetPosition(Side side);
}
