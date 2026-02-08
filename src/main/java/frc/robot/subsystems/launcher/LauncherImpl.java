package frc.robot.subsystems.launcher;

import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;
import static frc.robot.Constants.MOTOR_CONFIGURE_FREQUENCY;
import static frc.robot.Constants.Directories.*;
import static frc.robot.Constants.LauncherConstants.*;
import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.Throttler;
import frc.bofalib.BofaUtil;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.subsystem.CommandSchedulerWrapper;

// Colloquially known as Miles after bad Chinese
public final class LauncherImpl extends SubsystemBase implements Launcher {

    // 2 krakens

    public static enum Song {
        ;

        private final String filePath;

        Song(String simpleFileName) {
            this.filePath = String.format("%s/%s.chrp", SONGS_DIRECTORY, simpleFileName);
        }
    }

    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Launcher");

    private final LauncherMotors launcherMotors = new LauncherMotors();

    private final DoubleSupplier shootSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Shoot Speed"), 
        DEFAULT_SHOOT_SPEED, 
        CHECK_SIMPLE_MOTOR_SPEED
    );

    private final DoubleSupplier activeIdleSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Active Idle Speed"), 
        DEFAULT_ACTIVE_IDLE_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    private final DoubleSupplier inactiveIdleSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Inactive Idle Speed"), 
        DEFAULT_INACTIVE_IDLE_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    private final DoubleSupplier manualSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Manual Speed"), 
        DEFAULT_MANUAL_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    public LauncherImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            BofaUtil.composeConditional(
                launcherMotors.getConfigurator()::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    DEFAULT_CURRENT_LIMIT
                ), 
                BofaUtil.hasChangedDoublePredicate()
            ),
            BofaUtil.composeConditional(
                launcherMotors.getConfigurator()::applyRampUpPeriod, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Ramp Up Period"), 
                    DEFAULT_RAMP_UP_PERIOD
                ), 
                BofaUtil.hasChangedDoublePredicate()
            ),
            BofaUtil.compose(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Velocity RPM")
                ), 
                () -> (Rotations.per(Minute)).convertFrom(
                    launcherMotors.getCommanderVelocity(), 
                    RotationsPerSecond
                )
            )
        ));
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
        launcherMotors.setSpeed(shootSpeedSupplier.getAsDouble());
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
        launcherMotors.setSpeed(-shootSpeedSupplier.getAsDouble());
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
        launcherMotors.stop();
    }
}
