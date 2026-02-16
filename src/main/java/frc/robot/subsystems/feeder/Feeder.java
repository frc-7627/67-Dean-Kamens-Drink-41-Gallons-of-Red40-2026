package frc.robot.subsystems.feeder;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;

/**
 * Interface for the feeder mechanism of the robot.
 */
public interface Feeder extends Subsystem, Controllable<FeederControl> {
    static Feeder create() {
        return new FeederImpl();
    }
}
