package frc.bofalib.control;

public interface BiControllable<
    This extends BiControllable<This, FirstControl, SecondControl, Control>,
    FirstControl,
    SecondControl,
    Control extends BiControl<This, FirstControl, SecondControl>
> extends UniControllable<This, FirstControl, Control> {
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
