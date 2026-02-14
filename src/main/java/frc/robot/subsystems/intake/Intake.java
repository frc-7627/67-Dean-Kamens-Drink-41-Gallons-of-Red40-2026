package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;

/**
 * Interface for the intake mechanism of the robot.
 */
public interface Intake extends Subsystem, Controllable<IntakeControl> {

    static Intake create() {
        return new IntakeImpl();
    }
}
