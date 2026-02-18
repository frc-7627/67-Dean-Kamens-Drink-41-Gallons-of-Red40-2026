package frc.bofalib.hardware;

import frc.bofalib.control.Controllable;
import frc.bofalib.control.ViewableControl;
import frc.bofalib.loggable.Loggable;

public interface Hardware<Control extends Loggable, Config> extends 
    Controllable<Control>,
    ViewableControl<Control>, 
    Configurable<Config>,
    Loggable
{}
