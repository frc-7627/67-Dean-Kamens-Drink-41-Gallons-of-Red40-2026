package frc.robot.subsystems.feeder;

import frc.bofalib.control.Controllable;
import frc.bofalib.generic.music.MusicalSubsystem;

/**
 * Interface for the feeder mechanism of the robot.
 */
public interface Feeder extends MusicalSubsystem, Controllable<FeederControl> {
    static Feeder create() {
        return new FeederImpl();
    }
}
