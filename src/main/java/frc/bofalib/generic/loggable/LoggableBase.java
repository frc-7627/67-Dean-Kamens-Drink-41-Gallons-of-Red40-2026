package frc.bofalib.generic.loggable;

import frc.bofalib.loggable.Loggable;

public abstract class LoggableBase implements Loggable {
    private final String name;

    protected LoggableBase(String name) {
        this.name = name;
    }

    @Override
    public final String getLoggableName() {
        return name;
    }
}
