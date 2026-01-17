package frc.robot.subsystems.swervedrive;

import frc.robot.Constants;

import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;

public class LED {
    // TODO: move alliance enum somewhere else
    public enum Alliance {
        RED, BLUE
    }

    private Alliance alliance;

    private CANdle candle = new CANdle(0);
    // TODO: research more about new api for CANdle led and find hardware specifics
    private final SolidColor solid = new SolidColor(0, 0);

    /**
     * 
     * The subsystem implementing led indication. Do not use directly, use
     * {@code Indicator} for
     * indicating status.
     * 
     * @see frc.robot.subsystems.swervedrive.Indicator
     * @param alliance our team's alliance during competitions
     */
    public LED(Alliance alliance) {
        this.alliance = alliance;
    };

    /**
     * Get the idle color, for the appropriate alliance.
     * 
     * @return SolidColor the color for the LEDs for idle
     */
    SolidColor getIdleColor() {
        switch (alliance) {
            case RED -> {
                return solid.withColor(getColor(Constants.LEDConstants.IDLE_COLOR_RED));
            }
            case BLUE -> {
                return solid.withColor(getColor(Constants.LEDConstants.IDLE_COLOR_BLUE));
            }
            default -> {
                return null; // this case won't happen
            }
        }
    }

    /**
     * Get the completion color, for the appropriate alliance.
     * 
     * @return SolidColor the color for the LEDs for completion
     */
    SolidColor getCompletionColor() {
        switch (alliance) {
            case RED -> {
                return solid.withColor(getColor(Constants.LEDConstants.COMPLETION_COLOR_RED));
            }
            case BLUE -> {
                return solid.withColor(getColor(Constants.LEDConstants.COMPLETION_COLOR_BLUE));
            }
            default -> {
                return null; // this case won't happen
            }
        }
    }

    /**
     * Get the interruption color, for the appropriate alliance.
     * 
     * @return SolidColor the color for the LEDs during interruption
     */
    SolidColor getInterruptionColor() {
        switch (alliance) {
            case RED -> {
                return solid.withColor(getColor(Constants.LEDConstants.INTERRUPTION_COLOR_RED));
            }
            case BLUE -> {
                return solid.withColor(getColor(Constants.LEDConstants.INTERRUPTION_COLOR_BLUE));
            }
            default -> {
                return null;
            }
        }
    }

    // TODO: make the following three methods have consistent documentation style
    // with Indicator and
    // Logging.
    /**
     * Set the LED color to the idle setting.
     */
    public void indicateIdle() {
        candle.setControl(getIdleColor());
    }

    /**
     * Set the LED color to the completion setting
     */
    public void indicateCompletion() {
        candle.setControl(getCompletionColor());
    }

    /**
     * Set the LED color to the interruption setting
     */
    public void indicateInterruption() {
        candle.setControl(getInterruptionColor());
    }

    /**
     * Convert the integer RGB values into a RGBWColor object
     * 
     * @param color int[] array containing the 3 RGB values as int
     * @return RGBWColor the object used as the request for the LEDs
     */
    private RGBWColor getColor(int[] color) {
        return new RGBWColor(color[0], color[1], color[2]);
    }

}
