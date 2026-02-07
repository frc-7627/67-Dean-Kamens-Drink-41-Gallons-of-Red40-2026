package frc.robot.subsystems.indication;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.util.Progress;
import frc.robot.subsystems.gameinfo.GameInfoSupplier;

/**
 * Interface for the feedback and status of the robot, signaling the state and
 * events.
 */
public interface Indicator extends Subsystem {
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

    /**
     * Indicate the progress of a command with the current progress.
     * 
     * @param <CommandProgress> an amount of progress.
     * @param currentProgress   the current progress.
     */
    <CommandProgress extends Progress> void indicateProgress(CommandProgress currentProgress);

    static Indicator create(GameInfoSupplier gameInfoSupplier) {
        return new IndicatorImpl(gameInfoSupplier);
    }
}
