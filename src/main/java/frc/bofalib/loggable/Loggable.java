package frc.bofalib.loggable;

public interface Loggable {
    String getLoggableName();

    default String getLoggableInfo() {
        return getLoggableName();
    }
}
