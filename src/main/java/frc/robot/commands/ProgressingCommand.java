package frc.robot.commands;

import java.util.logging.Logger;

import frc.robot.commands.util.Progress;
import frc.robot.subsystems.indication.Indicator;

/**
 * A command that has progress.
 * 
 * @param <CommandProgress> an amount of progress.
 */
public class ProgressingCommand<CommandProgress extends Progress> extends IndicatingCommand {
    private CommandProgress currentProgress;

    /**
     * @param logger the logger to use.
     * @param indicator the indicator subsystem.
     * @param initialProgress the initial progress.
     */
    protected ProgressingCommand(Logger logger, Indicator indicator,
            CommandProgress initialProgress) {
        super(logger, indicator);

        this.currentProgress = initialProgress;
    }

    /**
     * @return the current progress.
     */
    protected CommandProgress getProgress() {
        return currentProgress;
    }

    /**
     * Sets the current progress to the new progress, and indicates the new progress.
     * 
     * @param newProgress the new progress.
     */
    protected void setProgress(CommandProgress newProgress) {
        this.currentProgress = newProgress;
        indicator.indicateProgress(logger, newProgress);
    }

    /**
     * Gets whether the command has finished. This is when the command progress is finished.
     * 
     * @return whether the command has finished.
     */
    @Override
    public boolean isFinished() {
        return currentProgress.isFinished();
    }
}
