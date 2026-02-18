package frc.bofalib.generic.control;

interface InnerControllable<Control> {
    default void beginControlInner(Control control) {}

    default void runControlInner(Control control) {}

    default void endControlInner(Control control) {}
}
