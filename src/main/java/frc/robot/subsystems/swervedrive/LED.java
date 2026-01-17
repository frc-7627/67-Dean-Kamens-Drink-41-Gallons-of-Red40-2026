package frc.robot.subsystems.swervedrive;

import frc.robot.Constants;

import com.ctre.phoenix6.controls.RainbowAnimation;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;

public class LED {

    private CANdle candle = new CANdle(0);
    // TODO: research more about new api for CANdle led and find hardware specifics
    private final SolidColor solid = new SolidColor(0, 0);

    public LED() {
    };

    /**
     * Set the LED color to the idle setting.
     */
    public void indicateIdle() {
        candle.setControl(solid.withColor(
                getColor(Constants.LEDConstants.IDLE_COLOR)));
    }

    /**
     * Set the LED color to the completion setting
     */
    public void indicateCompletion() {
        candle.setControl(solid.withColor(
                getColor(Constants.LEDConstants.COMPLETION_COLOR)));
    }

    /**
     * Set the LED color to the interruption setting
     */
    public void indicateInterruption() {
        candle.setControl(solid.withColor(
                getColor(Constants.LEDConstants.INTERRUPTION_COLOR)));
    }

    /**
     * Set the LED color to the alliance color
     */
    public void indicateAlliance() {
        candle.setControl(solid.withColor(
                getColor(Constants.LEDConstants.ALLIANCE_IDLE_COLOR)));
    }

    /**
     * Convert the integer RGB values into a RGBWColor object
     * 
     * @param color int[] array containing the 3 RGB values as int
     * @return
     */
    private RGBWColor getColor(int[] color) {
        return new RGBWColor(color[0], color[1], color[2]);
    }

}
