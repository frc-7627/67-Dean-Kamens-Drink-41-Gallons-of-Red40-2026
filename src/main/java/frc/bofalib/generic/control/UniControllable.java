package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;

public interface UniControllable<
    This extends UniControllable<This, FirstControl, Control>,
    FirstControl,
    Control extends UniControl<This, FirstControl>
> extends Controllable<Control> {
    Controllable<FirstControl> getFirstControllable();

    This getThis();

    @Override
    default void beginControl(Control control) {
        getFirstControllable().beginControl(control.getFirstControl(getThis()));
    }

    @Override
    default void runControl() {
        getFirstControllable().runControl();
    }

    @Override
    default void endControl() {
        getFirstControllable().endControl();
    }
}
