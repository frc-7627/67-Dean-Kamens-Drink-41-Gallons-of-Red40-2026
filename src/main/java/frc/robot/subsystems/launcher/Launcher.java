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
    public Launcher() {
        DashboardField.initAll(dashboardFields);
    }

    @Override
    public void periodic() {
        DashboardField.updateAll(dashboardFields);
    }

    /**
     * Plays the horn frequency as a note on the motors.
     * 
     * @see frc.robot.Constants.LauncherConstants#HORN_FREQ
     * @see LauncherMotors#playNote(int)
     * @apiNote Motors enter music mode.
     */
    public void playHornOnMotors() {
        launcherMotors.playNote(HORN_FREQ);
    }

    /**
     * Plays the provided song from its file on the motors.
     * 
     * @param song the provided song.
     * @see LauncherMotors#playSongFromFile(String)
     * @see Song
     * @apiNote Motors enter music mode.
     */
    public void playSongOnMotors(Song song) {
        launcherMotors.playSongFromFile(song.filePath);
    }

    /**
     * Exit music mode on the motors.
     */
    public void exitMusicModeOnMotors() {
        launcherMotors.exitMusicMode();
    }

    /**
     * Shoot out.
     * 
     * Sets the commander motor to the shoot speed.
     * 
     * @throws IllegalStateException if the motors are in music mode.
     * @see #shootSpeed
     * @see LauncherMotors#setCommanderSpeed(double)
     */
    public void shootOut() throws IllegalStateException {
        launcherMotors.setCommanderSpeed(shootSpeed.getInnerValue());
    }

    /**
     * Shoot in.
     * 
     * Sets the commander motor to the negative shoot speed.
     * 
     * @throws IllegalStateException if the motors are in music mode.
     * @apiNote Do not use unless in extraneous circumstances.
     * @see #shootSpeed
     * @see LauncherMotors#setCommanderSpeed(double)
     */
    public void shootIn() throws IllegalStateException {
        // TODO: why shouldn't this method be used unless in extraneous circumstances? Justify in
        // the api note.
        launcherMotors.setCommanderSpeed(-shootSpeed.getInnerValue());
    }

    /**
     * Manually shoot out.
     * 
     * Sets both motors to the manual speed.
     * 
     * @throws IllegalStateException if the motors are in music mode.
     * @see #manualSpeed
     * @see LauncherMotors#setBothSpeeds(double)
     */
    public void manualOutBoth() throws IllegalStateException {
        launcherMotors.setBothSpeeds(manualSpeed.getInnerValue());
    }

    /**
     * Manually shoot in.
     * 
     * Sets both motors to the negative manual speed.
     * 
     * @throws IllegalStateException if the motors are in music mode.
     * @see #manualSpeed
     * @see LauncherMotors#setBothSpeeds(double)
     */
    public void manualInBoth() throws IllegalStateException {
        launcherMotors.setBothSpeeds(-manualSpeed.getInnerValue());
    }

    /**
     * Stop the launcher.
     * 
     * Stops both motors.
     * 
     * @throws IllegalStateException if the motors are in music mode.
     * @see LauncherMotors#stopBoth()
     */
    public void stop() throws IllegalStateException {
        launcherMotors.stopBoth();
    }
}
