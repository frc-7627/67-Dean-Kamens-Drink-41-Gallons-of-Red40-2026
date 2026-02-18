package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;

public interface BoxControllableDefaultable<Control> extends 
    Controllable<Control>,
    InnerControllable<Control>  
{
    DefaultableControlBox<Control> getControlBox();

    @Override
    default void beginControl(Control control) {
        beginControlInner(control);

        getControlBox().setControl(control);
    }

    @Override
    default void runControl() {
        runControlInner(getControlBox().getControl());
    }

    @Override
    default void endControl() {
        endControlInner(getControlBox().getControl());

        getControlBox().resetControl();
    }
}
