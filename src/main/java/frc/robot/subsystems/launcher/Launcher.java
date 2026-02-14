package frc.robot.subsystems.launcher;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;

/**
 * Interface for the launcher mechanism of the robot.
 */
public interface Launcher extends Subsystem, Controllable<LauncherControl> {
    static Launcher create() {
        return new LauncherImpl();
    }
}
