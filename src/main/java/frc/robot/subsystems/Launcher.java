package frc.robot.subsystems;

import static frc.robot.Constants.Directories.SONGS_DIRECTORY;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.launcher.LauncherMotors;

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

    private final LauncherMotors launcherMotors;

    /** Initiallizes the Climber Subsystem */
    public Launcher(LauncherMotors launcherMotors) {
        this.launcherMotors = launcherMotors;

        reset();
    }

    /**
     * Plays a constant tone based on provided input using the talonFX controllers
     * 
     * Only use this when elevator is at 0
     * 
     * @param freq the frequency in hz of the tone
     * @return void
     * @version 1.0
     */
    public void playHorn() {
        // TODO: ensure elevator is at 0.
        launcherMotors.playNote(Constants.LauncherConstants.HORN_FREQ);
    }

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
    public void playSong(Song song) {
        // TODO: ensure elevator is at 0.
        launcherMotors.playSongFromFile(song.filePath);
    }

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


    /**
     * Spins the Launcher up at a speed specified in the instance variable ShootSpeed for general
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void shootOut() {
        // m_talonFX_Commander.set(ShootSpeed);
    }

    /**
     * Spins the Launcher in a counter clockwise direction at a speed specified in the instance
     * variable ShootSpeed for general TODO: make sure ^^ this is the right way control
     * 
     * @return void
     * @version 1.0
     */
    public void shootIn() {
        // m_talonFX_Commander.set(-ShootSpeed); // DO NOT USE UNLESS IN AN EXTRENUOUS CIRCUMSTANCE
    }

    /**
     * Slowly moves the launcher at a speed specified in the instance variable ManualSpeed for fine
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void manualOutBoth() {
        // m_talonFX_Commander.set(ManualSpeed);
        // m_talonFX_Minion.set(ManualSpeed);
    }

    /**
     * Slowly moves the launcher at a speed specified in the instance variable ManualSpeed for fine
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void manualInBoth() {
        // m_talonFX_Commander.set(-ManualSpeed);
        // m_talonFX_Minion.set(-ManualSpeed);
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
