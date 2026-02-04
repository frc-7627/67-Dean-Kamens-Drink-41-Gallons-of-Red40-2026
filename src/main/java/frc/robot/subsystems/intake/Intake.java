package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface for the intake mechanism of the robot.
 */
public interface Intake extends Subsystem {
    /**
     * Load a gamepiece with the intake.
     */
    void load();

    /**
     * Stops the intake.
     */
    void stop();

    static Intake create() {
        return new IntakeImpl();
    }
}
