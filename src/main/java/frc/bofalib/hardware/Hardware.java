package frc.bofalib.hardware;

import frc.bofalib.control.Controllable;
import frc.bofalib.loggable.Loggable;

public interface Hardware<Control, Config> extends 
    Controllable<Control>, 
    Configurable<Config>,
    Loggable
{}
