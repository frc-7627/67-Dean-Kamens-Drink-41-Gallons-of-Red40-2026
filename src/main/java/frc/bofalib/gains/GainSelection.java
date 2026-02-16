package frc.bofalib.gains;

public enum GainSelection {
    PROPORTIONAL("Proportional"),
    INTEGRAL("Integral"),
    DERIVATIVE("Derivative"),
    STATIC("Static"),
    VELOCITY("Velocity"),
    ACCELERATION("Acceleration"),
    GRAVITY("Gravity");

    public final String name;

    private GainSelection(String name) {
        this.name = name;
    }
}
