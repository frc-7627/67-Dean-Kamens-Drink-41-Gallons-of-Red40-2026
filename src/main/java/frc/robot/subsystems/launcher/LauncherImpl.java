package frc.robot.subsystems.launcher;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.Constants.CHECK_DUTY_CYCLE;
import static frc.robot.Constants.Directories.*;
import static frc.robot.Constants.LauncherConstants.*;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.BofaUtil;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.robot.Constants;

// Colloquially known as Miles after bad Chinese
final class LauncherImpl extends SubsystemBase implements Launcher {

    // 2 krakens

    // TODO: extract music interface
    public static enum Song {
        SUS("sus"),
        BAD_TO_THE_BONE("Bad To the Bone"),
        BLOODY_TEARS("bloodytears"),
        BLUE_LOBSTER("BlueLobster"),
        HCB("hcb"),
        PHOTOGRAPH("photograph"),
        RICKROLL("rickroll"),
        UNDERGROUND("Underground"),
        VSAUSE("vsauce"),
        WII_SHOP("Wii Shop");
        

        private final String filePath;

        Song(String simpleFileName) {
            this.filePath = String.format("%s/%s.chrp", SONGS_DIRECTORY, simpleFileName);
        }
    }

    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Launcher");
    private static final Logger LOGGER = Logger.getLogger(LauncherImpl.class.getName());

    private final Motors motors = new Motors();

    private final DoubleSupplier shootSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Shoot Speed"), 
        DEFAULT_SHOOT_SPEED, 
        CHECK_DUTY_CYCLE
    );

    private final DoubleSupplier activeIdleSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Active Idle Speed"), 
        DEFAULT_ACTIVE_IDLE_SPEED,
        CHECK_DUTY_CYCLE
    );

    private final DoubleSupplier inactiveIdleSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Inactive Idle Speed"), 
        DEFAULT_INACTIVE_IDLE_SPEED,
        CHECK_DUTY_CYCLE
    );

    private final DoubleSupplier manualSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Manual Speed"), 
        DEFAULT_MANUAL_SPEED,
        CHECK_DUTY_CYCLE
    );

    public LauncherImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            BofaUtil.composeConditional(
                motors::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    DEFAULT_CURRENT_LIMIT
                ), 
                BofaUtil.hasChangedDoublePredicate()
            ),
            BofaUtil.composeConditional(
                motors::applyRampUpPeriod, 
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
                () -> motors.getCommanderVelocity().in(RPM)
            )
        ));
    }

    /**
     * {@inheritDoc}
     * 
     * @see frc.robot.Constants.LauncherConstants#HORN_FREQ
     * @see Motors#playNote(int)
     */
    @Override
    public void playHornOnMotors() {
        motors.playNote(HORN_FREQ);
    }

    /**
     * {@inheritDoc}
     * 
     * @see Motors#playSongFromFile(String)
     * @see Song
     */
    @Override
    public void playSongOnMotors(Song song) {
        motors.playSongFromFile(song.filePath);
    }

    /**
     * @param linearFeetPerSec the given linear speed, in feet per second
     * @return the angular speed, in rotations per second, to achieve the given linear speed
     */
    private double getAngularSpeedRotPerSec(double linearFeetPerSec){
        return RotationsPerSecond.convertFrom(
            linearFeetPerSec / Constants.LauncherConstants.FLYWHEEL_RADIUS_FEET, 
            RadiansPerSecond
        );
    } 

    /**
     * {@inheritDoc}
     * 
     * Sets the commander motor to the shoot speed.
     * 
     * @see #oldShootSpeed
     * @see Motors#setCommanderSpeed(double)
     */
    @Override
    public void shootOut() {
        final double shootSpeedRotPerSec = getAngularSpeedRotPerSec(SHOOT_SPEED);
        LOGGER.finer("Shoot out speed: " + shootSpeedRotPerSec + " rot/sec");
        motors.setAngularSpeed(shootSpeedRotPerSec);
    }

    /**
     * {@inheritDoc}
     * 
     * Sets the commander motor to the negative shoot speed.
     * 
     * @apiNote Do not use unless in extraneous circumstances. CTBT WILL haunt you if you disobey or
     *          delete this.
     * @see #oldShootSpeed
     * @see Motors#setCommanderSpeed(double)
     */
    @Override
    public void shootIn() {
        motors.setSpeed(-shootSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * 
     * Stops both motors.
     * 
     * @see Motors#stopBoth()
     */
    @Override
    public void stop() {
        motors.stop();
    }
}
