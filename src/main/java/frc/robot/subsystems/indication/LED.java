package frc.robot.subsystems.indication;

import frc.robot.Constants;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
    private final CANdle candle = new CANdle(Constants.CanIDs.CANDLE_CAN_ID);

    /**
     * The subsystem implementing led indication. Do not use directly, use {@code Indicator} for
     * indicating status.
     * 
     * @see frc.robot.subsystems.Indicator
     */
    public LED() {}

    /**
     * Set the LEDs to a solid color of choice.
     * 
     * @param color The solid color of choice.
     */
    public void setSolidColor(RGBWColor color) {
        candle.setControl(Constants.LEDConstants.CONTROL_ALL_LEDS.withColor(color));
    }

    /**
     * Set the LEDs to a rainbow animation.
     */
    public void setRainbowAnimation() {
        candle.setControl(Constants.LEDConstants.RAINBOW_ANIMATION);
    }

    /**
     * Set the LEDs to be filled to the fraction of steps progressed to total steps with the
     * provided foreground and background color. 
     * 
     * @param stepsProgressed the steps progressed.
     * @param totalSteps the total steps.
     * @param fgColor the provided foreground color.
     * @param bgColor the provided background color.
     */
    public void setProgress(int stepsProgressed, int totalSteps, RGBWColor fgColor,
            RGBWColor bgColor) {
        int activeLEDs = (stepsProgressed * Constants.LEDConstants.ATTACHED_LED_COUNT) / totalSteps;

        int firstInactiveLEDNum = Constants.LEDConstants.FIRST_ATTACHED_LED_NUM + activeLEDs;
        int lastActiveLEDNum = firstInactiveLEDNum - 1;

        candle.setControl(
                new SolidColor(Constants.LEDConstants.FIRST_ATTACHED_LED_NUM, lastActiveLEDNum)
                        .withColor(fgColor));
        candle.setControl(
                new SolidColor(firstInactiveLEDNum, Constants.LEDConstants.LAST_ATTACHED_LED_NUM)
                        .withColor(bgColor));
    }
}
