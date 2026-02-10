package frc.bofalib.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface ControllableSubsystem<Control> extends Subsystem {
    void beginControl(Control control);

    void runControl();

    default void endControl() {}
}
