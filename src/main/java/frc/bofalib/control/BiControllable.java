package frc.bofalib.control;

public interface BiControllable<
    This extends BiControllable<This, FirstControl, SecondControl, Control>,
    FirstControl,
    SecondControl,
    Control extends BiControl<This, FirstControl, SecondControl>
> extends Controllable<Control> {
    This getThis();

    Controllable<FirstControl> getFirstControllable();

    Controllable<SecondControl> getSecondControllable();

    @Override
    default void beginControl(Control control) {
        getFirstControllable().beginControl(control.getFirstControl(getThis()));
        getSecondControllable().beginControl(control.getSecondControl(getThis()));
    }

    @Override
    default void runControl() {
        getFirstControllable().runControl();
        getSecondControllable().runControl();
    }

    @Override
    default void endControl() {
        getFirstControllable().endControl();
        getSecondControllable().endControl();
    }
}
