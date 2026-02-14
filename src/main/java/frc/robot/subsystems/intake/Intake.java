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
     * Spins the intake in the opposing direction of the load method
     */
    void eject();

    /**
     * Manually spins the intake at a slower speed inwards
     */
    void manualIn();

    /**
     * Manually spins the intakea ta  slower speed outwards
     */
    void manualOut();

    /**
     * Stops the intake.
     */
    void stopIntake();

    /**
     * Stops the Swivel
     */
    void stopSwivel();

    /**
     * Stops both motors at once
     */
    void stop();

    /**
     * Folds the intake out into the ready pos to intake fuel
     */
    void FoldOut();

    /**
     * Folds the intake back inside of the hopper
     */
    void FoldIn();

    static Intake create() {
        return new IntakeImpl();
    }
}
