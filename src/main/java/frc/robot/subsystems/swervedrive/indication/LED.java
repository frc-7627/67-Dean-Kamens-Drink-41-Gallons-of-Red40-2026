package frc.robot.subsystems.swervedrive.indication;

import frc.robot.Constants;
import frc.robot.subsystems.swervedrive.GameInfo;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;

public class LED {
    /**
     * Get the color from the given array.
     * 
     * @param array The given array.
     * @return The color.
     */
    private static RGBWColor getColorFromArray(int[] array) {
        return new RGBWColor(array[0], array[1], array[2]);
    }

    /**
     * Get the completion color.
     * 
     * @return The completion color.
     */
    private static RGBWColor getCompletionColor() {
        return getColorFromArray(Constants.LEDConstants.ColorArrays.COMPLETION_COLOR_ARRAY);
    }

    /**
     * Get the interruption color.
     * 
     * @return The interruption color.
     */
    private static RGBWColor getInterruptionColor() {
        return getColorFromArray(Constants.LEDConstants.ColorArrays.INTERRUPTION_COLOR_ARRAY);
    }

    private final GameInfo gameInfo;

    private CANdle candle = new CANdle(Constants.LEDConstants.CANDLE_CAN_ID);

    private final SolidColor solidColor = new SolidColor(0, Constants.LEDConstants.LAST_LED_NUM);

    /**
     * The subsystem implementing led indication. Do not use directly, use {@code Indicator} for
     * indicating status.
     * 
     * @see frc.robot.subsystems.swervedrive.Indicator
     * @param gameInfo The game information subsystem.
     */
    public LED(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    // TODO: make the following three methods have consistent documentation style
    // with Indicator and
    // Logging.
    /**
     * Set the LED color to the idle setting.
     */
    public void indicateIdle() {
        setSolidColor(getIdleColor());
    }

    /**
     * Set the LED color to the completion setting
     */
    public void indicateCompletion() {
        setSolidColor(getCompletionColor());
    }

    /**
     * Set the LED color to the interruption setting
     */
    public void indicateInterruption() {
        setSolidColor(getInterruptionColor());
    }

    /**
     * Get the idle color for the current alliance and phase.
     * 
     * @return The idle color for the current alliance and phase.
     */
    private RGBWColor getIdleColor() {
        return getColorFromArray(Constants.LEDConstants.ColorArrays.IDLE_COLOR_ARRAYS[gameInfo
                .getAllianceNum()][gameInfo.getPhaseNum()]);
    }

    /**
     * Set the LEDs to a solid color of choice.
     * 
     * @param color The solid color of choice.
     */
    private void setSolidColor(RGBWColor color) {
        candle.setControl(solidColor.withColor(color));
    }

}
