package frc.robot.subsystems.controllable.swivel;

import frc.bofalib.control.Controllable;
import frc.bofalib.generic.music.MusicalSubsystem;

public interface Swivel extends Controllable<SwivelControl> {
    static Swivel create() {
        return new SwivelImpl();
    }
}
