package frc.robot.subsystems.feeder;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface for the feeder mechanism of the robot.
 */
public interface Feeder extends Subsystem {
    /**
     * Turn on the conveyor to move the gamepiece from the intake to the hopper.
     */
    void feedIn();

    /**
     * Feed the opposite way.
     */
    void feedOut();

    /**
     * Runs the feeder Out at a slower, manual, rate
     */
    void manualFeedOut();

    /**
     * Runs the feeder In at a slower, manual, rate
     */
    void manualFeedIn();

    /**
     * Stop the feeder.
     */
    void stop();

    static Feeder create() {
        return new FeederImpl();
    }
}
