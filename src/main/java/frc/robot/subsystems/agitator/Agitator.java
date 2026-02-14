package frc.robot.subsystems.agitator;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;

/**
 * Interface for the hopper mechanism of the robot.
 */
public interface Agitator extends Subsystem, Controllable<AgitatorControl> {
    static Agitator create() {
        return new AgitatorImpl();
    }
}
