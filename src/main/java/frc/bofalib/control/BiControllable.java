package frc.bofalib.control;

public interface BiControllable<
    FirstControl,
    SecondControl,
    Control extends BiControl<FirstControl, SecondControl>
> extends Controllable<Control> {
    Controllable<FirstControl> getFirstControllable();

    Controllable<SecondControl> getSecondControllable();

    @Override
    default void beginControl(Control control) {
        getFirstControllable().beginControl(control.getFirstControl());
        getSecondControllable().beginControl(control.getSecondControl());
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
