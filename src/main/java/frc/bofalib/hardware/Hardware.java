package frc.bofalib.hardware;

import frc.bofalib.control.Controllable;

public interface Hardware<Control, Config> extends 
    Controllable<Control>, 
    Configurable<Config> 
{}
