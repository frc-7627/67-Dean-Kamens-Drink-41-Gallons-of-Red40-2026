package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;

public interface BoxControllable<Control> extends Controllable<Control> {
    ControlBox<Control> getControlBox();

    void runControlWith(Control control);

    @Override
    default void beginControl(Control control) {
        getControlBox().setControl(control);
    }

    @Override
    default void runControl() {
        getControlBox().getControl().ifPresent(
            this::runControlWith
        );
    }

    @Override
    default void endControl() {
        getControlBox().resetControl();
    }
}
