package frc.robot.subsystems.agitator;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface for the hopper mechanism of the robot.
 */
public interface Agitator extends Subsystem {
    static Agitator create() {
        return new AgitatorImpl();
    }
}
