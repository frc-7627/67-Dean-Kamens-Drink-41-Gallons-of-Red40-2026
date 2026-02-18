package frc.robot.subsystems.controllable.feeder;

import frc.bofalib.control.Controllable;
import frc.bofalib.generic.music.MusicalSubsystem;

public interface Feeder extends MusicalSubsystem, Controllable<FeederControl> {
    static Feeder create() {
        return new FeederImpl();
    }
}
