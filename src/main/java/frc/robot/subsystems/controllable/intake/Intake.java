package frc.robot.subsystems.controllable.intake;

import frc.bofalib.control.Controllable;
import frc.bofalib.generic.music.MusicalSubsystem;

public interface Intake extends MusicalSubsystem, Controllable<IntakeControl> {
    static Intake create() {
        return new IntakeImpl();
    }
}
