package frc.bofalib.dashboard.fields;

public class BadInitialValueError extends Error {
    BadInitialValueError(Object value) {
        super("Invalid initial value: " + value.toString() + "!");
    }
}
