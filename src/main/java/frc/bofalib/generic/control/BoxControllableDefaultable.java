package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;

public interface BoxControllableDefaultable<Control> extends Controllable<Control> {
    DefaultableControlBox<Control> getControlBox();

    void runControlWith(Control control);

    @Override
    default void beginControl(Control control) {
        getControlBox().setControl(control);
    }

    @Override
    default void runControl() {
        runControlWith(getControlBox().getControl());
    }

    @Override
    default void endControl() {
        getControlBox().resetControl();
    }
}
