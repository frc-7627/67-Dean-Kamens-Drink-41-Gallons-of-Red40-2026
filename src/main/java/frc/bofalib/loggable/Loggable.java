package frc.bofalib.loggable;

import java.util.logging.Logger;

public interface Loggable {
    String getLoggableName();

    default String getLoggableInfo() {
        return getLoggableName();
    }

    default Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }
}
