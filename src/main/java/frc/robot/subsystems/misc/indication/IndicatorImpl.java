package frc.robot.subsystems.misc.indication;

import com.ctre.phoenix6.signals.RGBWColor;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IndicatorConstants.ColorArrays.*;
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
        indicateInit();
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
        final AllianceDefaultColors defaultColors = switch (gameInfoSupplier.getAlliance()) {
            case Blue -> BLUE_DEFAULT_COLORS;
            case Red -> RED_DEFAULT_COLORS;
        };

        return getColorFromArray(
            switch (gameInfoSupplier.getPhase()) {
                case AUTO -> defaultColors.auto();
                case TELEOP -> defaultColors.active();
                case ENDGAME -> defaultColors.endgame();
            }
        );
    }

    /**
     * @return The completion color.
     */
    private static RGBWColor getCompletionColor() {
        return getColorFromArray(COMPLETION_COLOR_ARRAY);
    }

    /**
     * @return The interruption color.
     */
    private static RGBWColor getInterruptionColor() {
        return getColorFromArray(INTERRUPTION_COLOR_ARRAY);
    }

    /**
     * @return The progress bar color.
     */
    private static RGBWColor getProgressBarColor() {
        return getColorFromArray(PROGRESS_BAR_COLOR_ARRAY);
    }

    private static RGBWColor getElevatorUpColor() {
        return getColorFromArray(UP_COLOR_ARRAY);
    }

    private static RGBWColor getEjectingColor() {
        return getColorFromArray(EJECTING_COLOR_ARRAY);
    }

    private static RGBWColor getElevatorDownColor() {
        return getColorFromArray(DOWN_COLOR_ARRAY);
    }

    /**
     * @param array The given array.
     * @return The color.
     */
    private static RGBWColor getColorFromArray(int[] array) {
        return new RGBWColor(array[0], array[1], array[2]);
    }

    @Override
    public void indicateElevatorUp() {
        led.setSolidColor(getElevatorUpColor());
    }

    @Override
    public void indicateEjecting() {
        led.setSolidColor(getEjectingColor());
    }

    @Override
    public void indicateElevatorDown() {
        led.setSolidColor(getElevatorDownColor());
    }
}



/*                                                                                                                                                              
        ************                                                                                
     **************                                                                                 
   ****************    *************     *********** **************  ***********   **************   
  *********       * ****************** ************ ************** ************  *****************  
 ********          *******     ****************        *******   ********     * *******    ******** 
 ********         *******       ****** ************    ******    ******        *******      ******* 
 ************************      *******   ***********  *******   ********       *******     *******  
  *************** ******************* ****  *******   ******     ************* ******************   
    *************   ***************  *************   *******      ************  ***************     
        ******         ********        *******      *******          ********       *******         
                                                                                                    
 ############################## ##  ### ######  ##############   ###########  #####  ###   #####    
 ############################## ## ####### ##########   ######   ##### ######### ##  ###  ######    
 ################################### #### ###  ### ####### ##### ###############################                                                                                                    
 */
