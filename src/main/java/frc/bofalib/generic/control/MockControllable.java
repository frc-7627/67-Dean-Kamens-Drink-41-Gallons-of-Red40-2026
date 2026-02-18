package frc.bofalib.generic.control;

import frc.bofalib.control.Controllable;
import frc.bofalib.loggable.Loggable;

public interface MockControllable<Control extends Loggable> extends Controllable<Control>, Loggable {
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
