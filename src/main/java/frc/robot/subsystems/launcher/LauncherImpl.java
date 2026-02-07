package frc.robot.subsystems.launcher;

import static edu.wpi.first.units.Units.Hertz;
import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;
import static frc.robot.Constants.Directories.*;
import static frc.robot.Constants.LauncherConstants.*;
import java.util.function.DoubleSupplier;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.Util;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;

// Colloquially known as Miles after bad Chinese
public class LauncherImpl extends SubsystemBase implements Launcher {

    // 2 krakens

    public static enum Song {
        ;

        private final String filePath;

        Song(String simpleFileName) {
            this.filePath = String.format("%s/%s.chrp", SONGS_DIRECTORY, simpleFileName);
        }
    }

    private static final Frequency CONFIGURE_FREQUENCY = Hertz.of(5);
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(Launcher.class.getSimpleName());

    private final LauncherMotors launcherMotors = new LauncherMotors();

    private final DoubleSupplier currentLimitSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Current Limit"), 
        DEFAULT_CURRENT_LIMIT
    );

    private final DoubleSupplier rampUpPeriodSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Ramp Up Period"), 
        DEFAULT_RAMP_UP_PERIOD
    );

    private final DoubleSupplier shootSpeedSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Shoot Speed"), 
        DEFAULT_SHOOT_SPEED, 
        CHECK_SIMPLE_MOTOR_SPEED
    );

    private final DoubleSupplier activeIdleSpeedSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Active Idle Speed"), 
        DEFAULT_ACTIVE_IDLE_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    private final DoubleSupplier inactiveIdleSpeedSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Inactive Idle Speed"), 
        DEFAULT_INACTIVE_IDLE_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    private final DoubleSupplier manualSpeedSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Manual Speed"), 
        DEFAULT_MANUAL_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    @Override
    public void periodic() {
        final MotorsConfigurator configurator = launcherMotors.getConfigurator();

        Util.throttle(() -> configurator.apply(
            currentLimitSupplier.getAsDouble(), 
            rampUpPeriodSupplier.getAsDouble(),
            shootSpeedSupplier.getAsDouble()
        ), CONFIGURE_FREQUENCY);
    }

    /**
     * {@inheritDoc}
     * 
     * @see frc.robot.Constants.LauncherConstants#HORN_FREQ
     * @see LauncherMotors#playNote(int)
     */
    @Override
    public void playHornOnMotors() {
        launcherMotors.playNote(HORN_FREQ);
    }

    /**
     * {@inheritDoc}
     * 
     * @see LauncherMotors#playSongFromFile(String)
     * @see Song
     */
    @Override
    public void playSongOnMotors(Song song) {
        launcherMotors.playSongFromFile(song.filePath);
    }

    /**
     * {@inheritDoc}
     * 
     * Sets the commander motor to the shoot speed.
     * 
     * @see #oldShootSpeed
     * @see LauncherMotors#setCommanderSpeed(double)
     */
    @Override
    public void shootOut() {
        launcherMotors.setCommanderSpeed(shootSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * 
     * Sets the commander motor to the negative shoot speed.
     * 
     * @apiNote Do not use unless in extraneous circumstances.
     * @see #oldShootSpeed
     * @see LauncherMotors#setCommanderSpeed(double)
     */
    @Override
    public void shootIn() {
        // TODO: why shouldn't this method be used unless in extraneous circumstances?
        // Justify in
        // the api note.
        launcherMotors.setCommanderSpeed(-shootSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * 
     * Sets both motors to the manual speed.
     * 
     * @see #oldManualSpeed
     * @see LauncherMotors#setBothSpeeds(double)
     */
    @Override
    public void manualOutBoth() {
        launcherMotors.setBothSpeeds(manualSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * 
     * Sets both motors to the negative manual speed.
     * 
     * @see #oldManualSpeed
     * @see LauncherMotors#setBothSpeeds(double)
     */
    @Override
    public void manualInBoth() {
        launcherMotors.setBothSpeeds(-manualSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * 
     * Stops both motors.
     * 
     * @see LauncherMotors#stopBoth()
     */
    @Override
    public void stop() {
        launcherMotors.stopBoth();
    }
}
