package frc.robot.subsystems.launcher;

import static frc.robot.Constants.Directories.*;
import static frc.robot.Constants.LauncherConstants.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.launcher.dashboard.CurrentLimit;
import frc.robot.subsystems.launcher.dashboard.RampUpPeriod;
import frc.robot.subsystems.launcher.dashboard.ShootSpeed;
import frc.robot.subsystems.util.dashboard.DashboardField;
import frc.robot.subsystems.util.dashboard.MotorSpeed;

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

    private static final String SUBSYSTEM_NAME = LauncherImpl.class.getSimpleName();

    private final LauncherMotors launcherMotors = new LauncherMotors();

    private final CurrentLimit currentLimit = new CurrentLimit(launcherMotors.getConfigurator());
    private final RampUpPeriod rampUpPeriod = new RampUpPeriod(launcherMotors.getConfigurator());
    private final ShootSpeed shootSpeed = new ShootSpeed(launcherMotors.getConfigurator());

    private final MotorSpeed activeIdleSpeed =
            new MotorSpeed(SUBSYSTEM_NAME, "Active Idle Speed", DEFAULT_ACTIVE_IDLE_SPEED);
    private final MotorSpeed inactiveIdleSpeed =
            new MotorSpeed(SUBSYSTEM_NAME, "Inactive Idle Speed", DEFAULT_INACTIVE_IDLE_SPEED);
    private final MotorSpeed manualSpeed =
            new MotorSpeed(SUBSYSTEM_NAME, "Manual Speed", DEFAULT_MANUAL_SPEED);

    private final DashboardField[] dashboardFields = {currentLimit, rampUpPeriod, shootSpeed,
            activeIdleSpeed, inactiveIdleSpeed, manualSpeed};

    /**
     * The launcher subsystem.
     */
    LauncherImpl() {
        DashboardField.initAll(dashboardFields);
    }

    @Override
    public void periodic() {
        DashboardField.updateAll(dashboardFields);
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
     * @see #shootSpeed
     * @see LauncherMotors#setCommanderSpeed(double)
     */
    @Override
    public void shootOut() {
        launcherMotors.setCommanderSpeed(shootSpeed.getInnerValue());
    }

    /**
     * {@inheritDoc}
     * 
     * Sets the commander motor to the negative shoot speed.
     * 
     * @apiNote Do not use unless in extraneous circumstances.
     * @see #shootSpeed
     * @see LauncherMotors#setCommanderSpeed(double)
     */
    @Override
    public void shootIn() {
        // TODO: why shouldn't this method be used unless in extraneous circumstances?
        // Justify in
        // the api note.
        launcherMotors.setCommanderSpeed(-shootSpeed.getInnerValue());
    }

    /**
     * {@inheritDoc}
     * 
     * Sets both motors to the manual speed.
     * 
     * @see #manualSpeed
     * @see LauncherMotors#setBothSpeeds(double)
     */
    @Override
    public void manualOutBoth() {
        launcherMotors.setBothSpeeds(manualSpeed.getInnerValue());
    }

    /**
     * {@inheritDoc}
     * 
     * Sets both motors to the negative manual speed.
     * 
     * @see #manualSpeed
     * @see LauncherMotors#setBothSpeeds(double)
     */
    @Override
    public void manualInBoth() {
        launcherMotors.setBothSpeeds(-manualSpeed.getInnerValue());
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
