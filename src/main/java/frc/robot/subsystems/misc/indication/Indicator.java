package frc.robot.subsystems.misc.indication;

import frc.bofalib.generic.indication.ProgressIndicator;
import frc.robot.subsystems.shared.gameinfo.GameInfoSupplier;

/**
 * Interface for the feedback and status of the robot, signaling the state and
 * events.
 */
public interface Indicator extends ProgressIndicator {
    /**
     * Indicate that the robot is starting up.
     */
    void indicateStartup();

    /**
     * Indicate that a command was initialized.
     */
    void indicateInit();

    /**
     * Indicate that a command was completed.
     */
    void indicateCompletion();

    /**
     * Indicate that a command was interrupted.
     */
    void indicateInterruption();

    void indicateRamping();

    void indicateShooting();

    void indicateReRamping();

    static Indicator create(GameInfoSupplier gameInfoSupplier) {
        return new IndicatorImpl(gameInfoSupplier);
    }
}
