package frc.bofalib.control;

public interface UniControllable<
    FirstControl,
    Control extends UniControl<FirstControl>
> extends Controllable<Control> {
    Controllable<FirstControl> getFirstControllable();

    @Override
    default void beginControl(Control control) {
        getFirstControllable().beginControl(control.getFirstControl());
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
