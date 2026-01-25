package frc.robot.subsystems.indication;

import java.util.logging.Logger;
import com.ctre.phoenix6.signals.RGBWColor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.util.Progress;
import frc.robot.subsystems.GameInfo;

public class Indicator extends SubsystemBase {
    private final LED led = new LED();
    private final GameInfo gameInfo;

    /**
     * The subsystem for indicating any status.
     * 
     * @param gameInfo The game information singleton.
     */
    public Indicator(GameInfo gameInfo) {
        this.gameInfo = gameInfo;

        gameInfo.onAllianceSet(this::indicateNewAllianceSet);
    }

    /**
     * Indicate that a new alliance has been set.
     * 
     * Blinks the led with the new default color.
     */
    private void indicateNewAllianceSet() {
        //led.blinkWithColor(getDefaultColor());
        led.twinkWithColor(getDefaultColor());
    }

    /**
     * Indicate that a command was initialized with the provided logger.
     * 
     * Logs initialization and sets the LEDs to the default color.
     * 
     * @param logger The provided logger.
     */
    public void indicateInit(Logger logger) {
        logger.info("Initializing.");
        led.setSolidColor(getDefaultColor());
    }

    /**
     * Indicate that a command was completed with the provided logger.
     * 
     * Logs completion and sets the LEDs to the completion color.
     * 
     * @param logger The provided logger.
     */
    public void indicateCompletion(Logger logger) {
        logger.info("Completed.");
        led.setSolidColor(getCompletionColor());
    }

    /**
     * Indicate that a command was interrupted with the provided logger.
     * 
     * Logs interruption and sets the LEDs to the interruption color.
     * 
     * @param logger The provided logger.
     */
    public void indicateInterruption(Logger logger) {
        logger.info("Interrupted!");
        led.setSolidColor(getInterruptionColor());
    }

    /**
     * Indicate the progress of a command with the provided logger and current
     * progress.
     * 
     * Logs the current step and progress fraction, and fills the LEDs to the
     * fraction of steps
     * progressed to total steps, foreground being the progress bar color and
     * background being the
     * default color.
     * 
     * @param <CommandProgress> an amount of progress.
     * @param logger            the provided logger.
     * @param currentProgress   the current progress.
     */
    public <CommandProgress extends Progress> void indicateProgress(Logger logger,
            CommandProgress currentProgress) {
        // Log the current step and progress fraction.
        logger.fine(String.format("Now on step: \"{0}\". ({1}/{2})", currentProgress,
                currentProgress.getStepsProgressed(), currentProgress.getTotalSteps()));
        // Fill the LEDs to the fraction of steps progressed to total steps.
        led.setProgress(currentProgress.getStepsProgressed(), currentProgress.getTotalSteps(),
                getProgressBarColor(), getDefaultColor());
    }

    /**
     * @return The default color for the current alliance and phase.
     */
    private RGBWColor getDefaultColor() {
        return getColorFromArray(
                Constants.IndicatorConstants.ColorArrays.DEFAULT_COLOR_ARRAYS[gameInfo
                        .getAlliance().ordinal()][gameInfo.getPhase().ordinal()]);
    }

    /**
     * @return The completion color.
     */
    private static RGBWColor getCompletionColor() {
        return getColorFromArray(Constants.IndicatorConstants.ColorArrays.COMPLETION_COLOR_ARRAY);
    }

    /**
     * @return The interruption color.
     */
    private static RGBWColor getInterruptionColor() {
        return getColorFromArray(Constants.IndicatorConstants.ColorArrays.INTERRUPTION_COLOR_ARRAY);
    }

    /**
     * @return The progress bar color.
     */
    private static RGBWColor getProgressBarColor() {
        return getColorFromArray(Constants.IndicatorConstants.ColorArrays.PROGRESS_BAR_COLOR_ARRAY);
    }

    /**
     * @param array The given array.
     * @return The color.
     */
    private static RGBWColor getColorFromArray(int[] array) {
        return new RGBWColor(array[0], array[1], array[2]);
    }
}
