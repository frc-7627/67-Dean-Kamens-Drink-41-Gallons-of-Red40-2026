package frc.bofalib.generic.control;

import java.util.Optional;
import frc.bofalib.control.Controllable;
import frc.bofalib.control.ViewableControl;
import frc.bofalib.loggable.Loggable;

public interface BoxControllable<Control extends Loggable> extends 
    Controllable<Control>,
    InnerControllable<Control>,
    ViewableControl<Control>
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

    @Override
    default Optional<Control> getCurrentControlIfPresent() {
        return getControlBox().getControl();
    }
}
