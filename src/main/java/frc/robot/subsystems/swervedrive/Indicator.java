package frc.robot.subsystems.swervedrive;

import com.ctre.phoenix6.signals.RGBWColor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.swervedrive.indication.LED;

public class Indicator extends SubsystemBase {
    private final LED led;
    private final GameInfo gameInfo;

    /**
     * The subsystem for indicating any status.
     * 
     * Delegates implementation details to other subsystems(e.g. led, etc.).
     * 
     * @param gameInfo The game information subsystem.
     * @param led The LED subsystem.
     */
    public Indicator(GameInfo gameInfo, LED led) {
        this.gameInfo = gameInfo;
        this.led = led;
    }

    /**
     * Reset status.
     * 
     * Sets the LEDs to the default color.
     */
    public void resetStatus() {
        led.setSolidColor(getDefaultColor());
    }

    /**
     * Indicate that a command was completed. This should only be called in a command's {@code end}
     * method.
     * 
     * Sets the LEDs to the completion color.
     */
    public void indicateCompletion() {
        led.setSolidColor(getCompletionColor());
    }

    /**
     * Indicate that a command was interrupted. This should only be called in a command's
     * {@code end} method.
     * 
     * Sets the LEDs to the interruption color.
     */
    public void indicateInterruption() {
        led.setSolidColor(getInterruptionColor());
    }

    public void indicateProgress(int stepsProgressed, int totalSteps) {
        led.setProgress(stepsProgressed, totalSteps, getProgressBarColor(), getDefaultColor());
    }

    /**
     * @return The default color for the current alliance and phase.
     */
    private RGBWColor getDefaultColor() {
        return getColorFromArray(
                Constants.IndicatorConstants.ColorArrays.DEFAULT_COLOR_ARRAYS[gameInfo
                        .getAllianceNum()][gameInfo.getPhaseNum()]);
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
