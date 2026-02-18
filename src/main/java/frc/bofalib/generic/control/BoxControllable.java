package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;

public interface BoxControllable<Control> extends 
    Controllable<Control>,
    InnerControllable<Control> 
{
    ControlBox<Control> getControlBox();

    @Override
    default void beginControl(Control control) {
        beginControlInner(control);

        getControlBox().setControl(control);
    }

    @Override
    default void runControl() {
        getControlBox().getControl().ifPresent(
            this::runControlInner
        );
    }

    @Override
    default void endControl() {
        getControlBox().getControl().ifPresent(
            this::endControlInner
        );;

        getControlBox().resetControl();
    }
}
