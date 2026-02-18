package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;
import frc.bofalib.loggable.Loggable;

public interface UniControllable<
    This extends UniControllable<This, FirstControl, Control>,
    FirstControl,
    Control extends UniControl<This, FirstControl> & Loggable
> extends BoxControllable<Control> {
    Controllable<FirstControl> getFirstControllable();

    This getThis();

    @Override
    default void beginControlInner(Control control) {
        getFirstControllable().beginControl(control.getFirstControl(getThis()));
    }

    @Override
    default void runControlInner(Control control) {
        getFirstControllable().runControl();
    }

    @Override
    default void endControlInner(Control control) {
        getFirstControllable().endControl();
    }
}
