package frc.robot.subsystems.launcher;

import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;
import static frc.robot.Constants.Directories.*;
import static frc.robot.Constants.LauncherConstants.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robotlib.dashboard.fields.PullingDouble;

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

    private static final String DASHBOARD_NAME = Launcher.class.getSimpleName();

    private final LauncherMotors launcherMotors = new LauncherMotors();

    private final PullingDouble currentLimit = new PullingDouble(
        DASHBOARD_NAME, 
        "Current Limit",
        launcherMotors.getConfigurator()::applyCurrentLimit, 
        DEFAULT_CURRENT_LIMIT
    );

    private final PullingDouble rampUpPeriod = new PullingDouble(
        DASHBOARD_NAME, 
        "Ramp Up Period",
        launcherMotors.getConfigurator()::applyRampUpPeriod, 
        DEFAULT_RAMP_UP_PERIOD
    );

    private final PullingDouble shootSpeed = new PullingDouble(
        DASHBOARD_NAME, 
        "Shoot Speed", 
        CHECK_SIMPLE_MOTOR_SPEED,
        launcherMotors.getConfigurator()::applyShootSpeed, 
        DEFAULT_SHOOT_SPEED
    );

    private final PullingDouble activeIdleSpeed = new PullingDouble(
        DASHBOARD_NAME,
        "Active Idle Speed", 
        CHECK_SIMPLE_MOTOR_SPEED, 
        DEFAULT_ACTIVE_IDLE_SPEED
    );

    private final PullingDouble inactiveIdleSpeed = new PullingDouble(
        DASHBOARD_NAME,
        "Inactive Idle Speed", 
        CHECK_SIMPLE_MOTOR_SPEED, 
        DEFAULT_INACTIVE_IDLE_SPEED
    );

    private final PullingDouble manualSpeed = new PullingDouble(DASHBOARD_NAME, "Manual Speed",
            CHECK_SIMPLE_MOTOR_SPEED, DEFAULT_MANUAL_SPEED);

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
        launcherMotors.setCommanderSpeed(shootSpeed.getPulled());
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
        launcherMotors.setCommanderSpeed(-shootSpeed.getPulled());
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
        launcherMotors.setBothSpeeds(manualSpeed.getPulled());
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
        launcherMotors.setBothSpeeds(-manualSpeed.getPulled());
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
