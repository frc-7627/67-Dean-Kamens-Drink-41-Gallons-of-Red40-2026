package frc.robot.subsystems.swervedrive.indication;

import frc.robot.Constants;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
    private final CANdle candle = new CANdle(Constants.LEDConstants.CANDLE_CAN_ID);

    private final SolidColor solidColor = new SolidColor(0, Constants.LEDConstants.LAST_LED_NUM);

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
        candle.setControl(solidColor.withColor(color));
    }
}
