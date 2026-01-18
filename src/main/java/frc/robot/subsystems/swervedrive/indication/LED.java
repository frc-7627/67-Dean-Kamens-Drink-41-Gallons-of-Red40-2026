package frc.robot.subsystems.swervedrive.indication;

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
     * @see frc.robot.subsystems.swervedrive.Indicator
     */
    public LED() {}

    /**
     * Set the LEDs to a solid color of choice.
     * 
     * @param color The solid color of choice.
     */
    public void setSolidColor(RGBWColor color) {
        candle.setControl(Constants.LEDConstants.ALL_LEDS.withColor(color));
    }

    public void setProgress(RGBWColor fgColor, RGBWColor bgColor) {
        // TODO: compute active LEDs
        int activeLEDS = 0;

        int firstInactiveLEDNum = Constants.LEDConstants.FIRST_ATTACHED_LED_NUM + activeLEDS;
        int lastActiveLEDNum = firstInactiveLEDNum - 1;

        candle.setControl(new SolidColor(Constants.LEDConstants.FIRST_ATTACHED_LED_NUM, lastActiveLEDNum).withColor(fgColor));
        candle.setControl(new SolidColor(firstInactiveLEDNum, Constants.LEDConstants.LAST_ATTACHED_LED_NUM).withColor(bgColor));
    }
}
