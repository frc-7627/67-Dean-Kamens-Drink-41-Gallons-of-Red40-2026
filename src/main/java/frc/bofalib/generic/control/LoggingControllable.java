package frc.bofalib.generic.control;

import frc.bofalib.loggable.Loggable;

public interface LoggingControllable<Control extends Loggable> extends
    BoxControllable<Control>,
    Loggable
{
    @Override
    default void beginControl(Control control) {
        getLogger().finer(
            () -> "Entering control in " + getLoggableName() + ": " + control.getLoggableInfo()
        );
        BoxControllable.super.beginControl(control);
    }

    @Override
    default void runControl() {
        getCurrentControlIfPresent().ifPresent(
            control -> getLogger().finest(
                () -> "Running control in " + getLoggableName() + ": " + control.getLoggableInfo()
            )
        );
        BoxControllable.super.runControl();
    }

    @Override
    default void endControl() {
        getCurrentControlIfPresent().ifPresent(
            control -> getLogger().finer(
                () -> "Exiting control in " + getLoggableName() + ": " + control.getLoggableInfo()    
            )
        );
        BoxControllable.super.endControl();
    }
}
