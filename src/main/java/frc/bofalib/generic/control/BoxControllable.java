package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;

public interface BoxControllable<Control> extends Controllable<Control> {
    ControlBox<Control> getControlBox();

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
        getControlBox().getControl().ifPresent(
            this::runControlWith
        );
    }

    @Override
    default void endControl() {
        getControlBox().getControl().ifPresent(
            this::endControlWith
        );;

        getControlBox().resetControl();
    }
}
