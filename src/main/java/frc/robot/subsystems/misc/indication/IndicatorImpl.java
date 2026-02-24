package frc.robot.subsystems.misc.indication;

import com.ctre.phoenix6.signals.RGBWColor;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.shared.gameinfo.GameInfoSupplier;

final class IndicatorImpl extends SubsystemBase implements Indicator {
    private final Startup startup = new Startup();
    private final LED led = new LED();
    private final GameInfoSupplier gameInfoSupplier;

    /**
     * The subsystem for indicating any status.
     * 
     * @param gameInfoSupplier The game information supplier resource.
     */
    public IndicatorImpl(GameInfoSupplier gameInfoSupplier) {
        this.gameInfoSupplier = gameInfoSupplier;

        gameInfoSupplier.onAllianceSet(this::indicateNewAllianceSet);
    }

    /**
     * {@inheritDoc}
     * 
     * @see Startup#startup()
     */
    @Override
    public void indicateStartup() {
        startup.startup();
    }

    /**
     * Indicate that a new alliance has been set.
     * 
     * Twinks the led with the new default color.
     */
    private void indicateNewAllianceSet(Alliance alliance) {
        led.twinkWithColor(getDefaultColor());
    }

    /**
     * {@inheritDoc}
     * 
     * Logs initialization and sets the LEDs to the default color.
     */
    @Override
    public void indicateInit() {
        led.setSolidColor(getDefaultColor());
    }

    /**
     * {@inheritDoc}
     * 
     * Logs completion and sets the LEDs to the completion color.
     */
    @Override
    public void indicateCompletion() {
        led.setSolidColor(getCompletionColor());
    }

    /**
     * {@inheritDoc}
     * 
     * Logs interruption and sets the LEDs to the interruption color.
     */
    @Override
    public void indicateInterruption() {
        led.setSolidColor(getInterruptionColor());
    }

    /**
     * {@inheritDoc}
     * 
     * Logs the current step and progress fraction, and fills the LEDs to the fraction of steps
     * progressed to total steps, foreground being the progress bar color and background being the
     * default color.
     */
    @Override
    public void indicateProgress(int stepsProgressed, int totalSteps) {
        led.setProgress(stepsProgressed, totalSteps, getProgressBarColor(), getDefaultColor());
    }

    /**
     * @return The default color for the current alliance and phase.
     */
    private RGBWColor getDefaultColor() {
        return getColorFromArray(
                Constants.IndicatorConstants.ColorArrays.DEFAULT_COLOR_ARRAYS[gameInfoSupplier
                        .getAlliance().ordinal()][gameInfoSupplier.getPhase().ordinal()]);
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

    @Override
    public void indicateRampUp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indicateRampUp'");
    }

    @Override
    public void indicateShoot() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indicateShoot'");
    }

    @Override
    public void indicateGrace() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indicateGrace'");
    }
}
