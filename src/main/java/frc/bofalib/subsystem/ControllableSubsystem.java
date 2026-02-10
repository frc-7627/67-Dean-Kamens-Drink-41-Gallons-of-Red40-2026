package frc.bofalib.subsystem;

public interface ControllableSubsystem<Control> {
    void setControl(Control control);

    void resetControl();
}
