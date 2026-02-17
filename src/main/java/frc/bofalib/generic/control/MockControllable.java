package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;

public interface MockControllable<Control> extends Controllable<Control> {
    @Override
    default void beginControl(Control control) {
        // TODO Auto-generated method stub
    }

    @Override
    default void runControl() {
        // TODO Auto-generated method stub
    }

    @Override
    default void endControl() {
        // TODO Auto-generated method stub
    }
}
