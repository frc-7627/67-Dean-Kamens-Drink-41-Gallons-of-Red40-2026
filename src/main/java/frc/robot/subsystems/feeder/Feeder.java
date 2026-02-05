package frc.robot.subsystems.feeder;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface for the feeder mechanism of the robot.
 */
public interface Feeder extends Subsystem {
    static Feeder create() {
        return new FeederImpl();
    }
}
