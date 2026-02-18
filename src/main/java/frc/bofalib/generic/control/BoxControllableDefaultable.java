package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;

public interface BoxControllableDefaultable<Control> extends Controllable<Control> {
    DefaultableControlBox<Control> getControlBox();

    default void beginControlWith(Control control) {}
    
    void runControlWith(Control control);

    default void endControlWith(Control control) {}

    @Override
    default void beginControl(Control control) {
        beginControlWith(control);

        getControlBox().setControl(control);
    }

    @Override
    default void runControl() {
        runControlWith(getControlBox().getControl());
    }

    @Override
    default void endControl() {
        endControlWith(getControlBox().getControl());

        getControlBox().resetControl();
    }
}
