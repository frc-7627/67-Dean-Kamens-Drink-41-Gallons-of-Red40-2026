package frc.bofalib.generic.control;

import java.util.Optional;

public final class ControlBox<Control> {
    private Optional<Control> controlOptional = Optional.empty();

    public ControlBox() {}

    public Optional<Control> getControl() {
        return controlOptional;
    }

    public void setControl(Control control) {
        this.controlOptional = Optional.of(control);
    }

    public void resetControl() {
        this.controlOptional = Optional.empty();
    }
}
