package frc.robot.subsystems.controllable.Endaffector;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;

/**
 * The agitator.
 */
public interface Endaffector extends Subsystem, Controllable<EndaffectorControl> {
    static Endaffector create() {
        return new EndaffectorImpl();
    }
}
