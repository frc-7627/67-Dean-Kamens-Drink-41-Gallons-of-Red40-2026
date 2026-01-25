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

    /** Initiallizes the Climber Subsystem */
    public Launcher() {
        DashboardField.initAll(dashboardFields);

        reset();
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

    // TODO: fix docs
    /**
     * Plays a constant tone based on provided input using the talonFX controllers
     * 
     * Only use this when elevator is at 0
     * 
     * @param freq the frequency in hz of the tone
     * @return void
     * @version 1.0
     */
    public boolean playHorn() {
        if (isSafeToPlayMusic()) {
            launcherMotors.playNote(Constants.LauncherConstants.HORN_FREQ);
            return true;
        }

        return false;
    }

    // TODO: fix docs
    /**
     * Plays a CHRP file using Pheonix Orchestra using both TalonFX motor controllers, limited to
     * the amount of talonFXs used by subsystem
     *
     * Only use this when elevator is at 0
     *
     * MIDI files can be converted to CHRP files in the Pheonix Tuner X utilites
     * 
     * @param filename the name of the CHRP file as String (without the extension)
     * @return void
     * @version 1.0
     */
    public boolean playSong(Song song) {
        if (isSafeToPlayMusic()) {
            launcherMotors.playSongFromFile(song.filePath);
            return true;
        }

        return false;
    }

    // TODO: fix docs
    /**
     * Resets the control modes of both TalonFXs Must use after playing audio on the Motor
     * Controllers To revert them back to position control for elevator use
     * 
     * @return void
     * @version 1.0
     */
    public void reset() {
        launcherMotors.reset();
    }


    // TODO: fix docs
    /**
     * Spins the Launcher up at a speed specified in the instance variable ShootSpeed for general
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void shootOut() {
        launcherMotors.setCommander(shootSpeed.getInnerValue());
    }

    // TODO: fix docs
    /**
     * Spins the Launcher in a counter clockwise direction at a speed specified in the instance
     * variable ShootSpeed for general TODO: make sure ^^ this is the right way control
     * 
     * @return void
     * @version 1.0
     */
    public void shootIn() {
        // TODO: why shouldn't this method be used unless in extraneous circumstances? Answer in docs.
        launcherMotors.setCommander(shootSpeed.getInnerValue());
    }

    // TODO: fix docs
    /**
     * Slowly moves the launcher at a speed specified in the instance variable ManualSpeed for fine
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void manualOutBoth() {
        launcherMotors.setBoth(manualSpeed.getInnerValue());
    }

    // TODO: fix docs
    /**
     * Slowly moves the launcher at a speed specified in the instance variable ManualSpeed for fine
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void manualInBoth() {
        launcherMotors.setBoth(-manualSpeed.getInnerValue());
    }

    /**
     * Stops all Motors
     * 
     * @return void
     * @version 1.0
     */
    public void stop() {
        launcherMotors.stop();
    }
}
