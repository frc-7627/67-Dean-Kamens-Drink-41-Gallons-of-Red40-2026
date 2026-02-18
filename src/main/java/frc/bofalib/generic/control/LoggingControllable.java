package frc.bofalib.generic.control;

import frc.bofalib.loggable.Loggable;

public interface LoggingControllable<Control extends Loggable> extends
    BoxControllable<Control>,
    Loggable
{
    @Override
    default void beginControl(Control control) {
        // TODO Auto-generated method stub
        BoxControllable.super.beginControl(control);
    }

    @Override
    default void runControl() {
        // TODO Auto-generated method stub
        BoxControllable.super.runControl();
    }

    @Override
    default void endControl() {
        // TODO Auto-generated method stub
        BoxControllable.super.endControl();
    }
}
