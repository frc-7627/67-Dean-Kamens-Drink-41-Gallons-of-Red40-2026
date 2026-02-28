package frc.bofalib.generic.control;

import frc.bofalib.loggable.Loggable;

public interface MockControllable<Control extends Loggable> extends LoggingControllable<Control> {
    @Override
    default void beginControlInner(Control control) {
        // Do nothing
    }

    @Override
    default void runControlInner(Control control) {
        // Do nothing
    }

    @Override
    default void endControlInner(Control control) {
        // Do nothing
    }
}
