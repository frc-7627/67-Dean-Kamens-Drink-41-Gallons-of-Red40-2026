package frc.robot.subsystems.controllable.climber;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;

/**
 * The climber
 */
public interface Climber extends Subsystem, Controllable<ClimberControl> {
    static Climber create() {
        return new ClimberImpl();
    }

}
