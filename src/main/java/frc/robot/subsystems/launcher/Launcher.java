package frc.robot.subsystems.launcher;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.launcher.LauncherImpl.Song;

/**
 * Interface for the launcher mechanism of the robot.
 */
public interface Launcher extends Subsystem {
    /**
     * Plays the horn frequency as a note on the motors.
     */
    void playHornOnMotors();

    /**
     * Plays the provided song from its file on the motors.
     * 
     * @param song the provided song.
     */
    void playSongOnMotors(Song song);
    
    /**
     * Shoot out.
     */
    void shootOut();

    /**
     * Shoot in.
     */
    void shootIn();

    /**
     * Stop the launcher.
     */
    void stop();

    
    static Launcher create() {
        return new LauncherImpl();
    }
}
