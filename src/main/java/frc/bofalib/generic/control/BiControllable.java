package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;
import frc.bofalib.loggable.Loggable;

public interface BiControllable<
    This extends BiControllable<This, FirstControl, SecondControl, Control>,
    FirstControl,
    SecondControl,
    Control extends BiControl<This, FirstControl, SecondControl> & Loggable
> extends UniControllable<This, FirstControl, Control> {
    Controllable<SecondControl> getSecondControllable();

    @Override
    default void beginControlInner(Control control) {
        getFirstControllable().beginControl(control.getFirstControl(getThis()));
        getSecondControllable().beginControl(control.getSecondControl(getThis()));
    }

    @Override
    default void runControlInner(Control control) {
        getFirstControllable().runControl();
        getSecondControllable().runControl();
    }

    @Override
    default void endControlInner(Control control) {
        getFirstControllable().endControl();
        getSecondControllable().endControl();
    }
}
