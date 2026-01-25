package frc.robot.subsystems.launcher;

import static frc.robot.Constants.Directories.*;
import static frc.robot.Constants.LauncherConstants.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.launcher.dashboard.CurrentLimit;
import frc.robot.subsystems.launcher.dashboard.RampUpPeriod;
import frc.robot.subsystems.launcher.dashboard.ShootSpeed;
import frc.robot.subsystems.util.dashboard.DashboardField;
import frc.robot.subsystems.util.dashboard.MotorSpeed;

// Colloquially known as Miles after bad Chinese
public class Launcher extends SubsystemBase {

    // 2 krakens

    public static enum Song {
        ;

        private final String filePath;

        Song(String simpleFileName) {
            this.filePath = String.format("%s/%s.chrp", SONGS_DIRECTORY, simpleFileName);
        }
    }

    private static final String SUBSYSTEM_NAME = Launcher.class.getSimpleName();

    private final LauncherMotors launcherMotors = new LauncherMotors();

    private final CurrentLimit currentLimit = new CurrentLimit(launcherMotors.getConfigurator());
    private final RampUpPeriod rampUpPeriod = new RampUpPeriod(launcherMotors.getConfigurator());
    private final ShootSpeed shootSpeed = new ShootSpeed(launcherMotors.getConfigurator());

    private final MotorSpeed activeIdleSpeed =
            new MotorSpeed(SUBSYSTEM_NAME, "Active Idle Speed", DEFAULT_ACTIVE_IDLE);
    private final MotorSpeed inactiveIdleSpeed =
            new MotorSpeed(SUBSYSTEM_NAME, "Inactive Idle Speed", DEFAULT_INACTIVE_IDLE);
    private final MotorSpeed manualSpeed =
            new MotorSpeed(SUBSYSTEM_NAME, "Manual Speed", DEFAULT_MANUAL_SPEED);

    private final DashboardField[] dashboardFields = {currentLimit, rampUpPeriod, shootSpeed,
            activeIdleSpeed, inactiveIdleSpeed, manualSpeed};

    /**
     * The launcher subsystem.
     */
    public Launcher() {
        DashboardField.initAll(dashboardFields);
    }

    @Override
    public void periodic() {
        DashboardField.updateAll(dashboardFields);
    }

    /**
     * @return whether it is currently safe to play music.
     */
    private boolean isSafeToPlayMusic() {
        // TODO
        return true;
    }

    /**
     * Plays the horn if it is safe to do so.
     * 
     * @return whether it was safe to do so.
     */
    public boolean playHornIfSafe() {
        if (isSafeToPlayMusic()) {
            launcherMotors.playNote(Constants.LauncherConstants.HORN_FREQ);
            return true;
        }

        return false;
    }

    /**
     * Plays the provided song if it is safe to do so.
     * 
     * @param song the provided song.
     * @return whether it was safe to do so.
     * @see #isSafeToPlayMusic()
     * @see LauncherMotors#playSongFromFile(String)
     * @see Song
     */
    public boolean playSongIfSafe(Song song) {
        if (isSafeToPlayMusic()) {
            launcherMotors.playSongFromFile(song.filePath);
            return true;
        }

        return false;
    }

    /**
     * Reset the launcher.
     * 
     * Resets the launcher motors.
     * 
     * @see LauncherMotors#reset()
     */
    public void reset() {
        launcherMotors.reset();
    }


    /**
     * Shoot out.
     * 
     * Sets the commander motor to the shoot speed.
     * 
     * @see #shootSpeed
     * @see LauncherMotors#setCommanderSpeed(double)
     */
    public void shootOut() {
        launcherMotors.setCommanderSpeed(shootSpeed.getInnerValue());
    }

    /**
     * Shoot in.
     * 
     * Sets the commander motor to the negative shoot speed.
     * 
     * @see #shootSpeed
     * @see LauncherMotors#setCommanderSpeed(double)
     * @apiNote Do not use unless in extraneous circumstances.
     */
    public void shootIn() {
        // TODO: why shouldn't this method be used unless in extraneous circumstances? Justify in
        // the api note.
        launcherMotors.setCommanderSpeed(-shootSpeed.getInnerValue());
    }

    /**
     * Manually shoot out.
     * 
     * Sets both motors to the manual speed.
     * 
     * @see #manualSpeed
     * @see LauncherMotors#setBothSpeeds(double)
     */
    public void manualOutBoth() {
        launcherMotors.setBothSpeeds(manualSpeed.getInnerValue());
    }

    /**
     * Manually shoot in.
     * 
     * Sets both motors to the negative manual speed.
     * 
     * @see #manualSpeed
     * @see LauncherMotors#setBothSpeeds(double)
     */
    public void manualInBoth() {
        launcherMotors.setBothSpeeds(-manualSpeed.getInnerValue());
    }

    /**
     * Stop the launcher.
     * 
     * Stops both motors.
     * 
     * @see LauncherMotors#stopBoth()
     */
    public void stop() {
        launcherMotors.stopBoth();
    }
}
