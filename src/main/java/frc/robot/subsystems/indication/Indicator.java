package frc.robot.subsystems.indication;

import java.util.logging.Logger;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.commands.util.Progress;
import frc.robot.resources.gameinfo.GameInfoSupplier;

public interface Indicator extends Subsystem {
    /**
     * Indicate that the robot is starting up.
     */
    void indicateStartup();

    /**
     * Indicate that a command was initialized with the provided logger.
     * 
     * @param logger The provided logger.
     */
    void indicateInit(Logger logger);

    /**
     * Indicate that a command was completed with the provided logger.
     * 
     * @param logger The provided logger.
     */
    void indicateCompletion(Logger logger);

    /**
     * Indicate that a command was interrupted with the provided logger.
     * 
     * @param logger The provided logger.
     */
    void indicateInterruption(Logger logger);

    /**
     * Indicate the progress of a command with the provided logger and current progress.
     * 
     * @param <CommandProgress> an amount of progress.
     * @param logger the provided logger.
     * @param currentProgress the current progress.
     */
    <CommandProgress extends Progress> void indicateProgress(Logger logger,
            CommandProgress currentProgress);

    static Indicator create(GameInfoSupplier gameInfoSupplier) {
        return new IndicatorImpl(gameInfoSupplier);
    }
}
