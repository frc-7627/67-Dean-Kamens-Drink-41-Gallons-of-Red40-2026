package frc.bofalib.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Subsystems that can be controlled using a control strategy.
 * 
 * @param <Control> control strategies
 */
public interface ControllableSubsystem<Control> extends Subsystem {
    /**
     * Sets the control strategy to the provided control strategy.
     * 
     * @param control the provided control strategy.
     */
    void beginControl(Control control);

    /**
     * Execute the control strategy.
     */
    void runControl();

    /**
     * Do any required work to cleanup using the control strategy.
     */
    default void endControl() {}
}
