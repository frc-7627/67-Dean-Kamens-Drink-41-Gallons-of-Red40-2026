package frc.robot.subsystems.controllable.agitator;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;

/**
 * The agitator.
 */
public interface Agitator extends Subsystem, Controllable<AgitatorControl> {
    static Agitator create() {
        return new AgitatorImpl();
    }
}
