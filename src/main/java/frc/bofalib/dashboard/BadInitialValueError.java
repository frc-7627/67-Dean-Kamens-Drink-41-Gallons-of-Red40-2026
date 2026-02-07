package frc.bofalib.dashboard;

public class BadInitialValueError extends Error {
    BadInitialValueError(Object value) {
        super("Invalid initial value: " + value.toString() + "!");
    }
}
