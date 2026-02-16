package frc.robot.subsystems.controllable.launcher;

import frc.bofalib.control.Controllable;
import frc.bofalib.generic.music.MusicalSubsystem;

/**
 * Interface for the launcher mechanism of the robot.
 */
public interface Launcher extends MusicalSubsystem, Controllable<LauncherControl> {
    static Launcher create() {
        return new LauncherImpl();
    }
}
