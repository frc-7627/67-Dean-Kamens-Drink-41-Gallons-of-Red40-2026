package frc.robot.subsystems.hopper;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface for the hopper mechanism of the robot.
 */
public interface Hopper extends Subsystem {
    static Hopper create() {
        return new HopperImpl();
    }
}
