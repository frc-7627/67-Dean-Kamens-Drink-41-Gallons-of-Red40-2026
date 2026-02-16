package frc.bofalib.gains;

import java.util.Objects;

public final class GainItem {
    public final GainSelection selection;
    public final double defaultValue;

    private GainItem(GainSelection selection, double defaultValue) {
        this.selection = Objects.requireNonNull(selection);
        this.defaultValue = defaultValue;
    }

    public static GainItem createProportional(double defaultValue) {
        return new GainItem(GainSelection.PROPORTIONAL, defaultValue);
    }

    public static GainItem createIntegral(double defaultValue) {
        return new GainItem(GainSelection.INTEGRAL, defaultValue);
    }

    public static GainItem createDerivative(double defaultValue) {
        return new GainItem(GainSelection.DERIVATIVE, defaultValue);
    }

    public static GainItem createStatic(double defaultValue) {
        return new GainItem(GainSelection.STATIC, defaultValue);
    }

    public static GainItem createVelocity(double defaultValue) {
        return new GainItem(GainSelection.VELOCITY, defaultValue);
    }

    public static GainItem createAcceleration(double defaultValue) {
        return new GainItem(GainSelection.ACCELERATION, defaultValue);
    }

    public static GainItem createGravity(double defaultValue) {
        return new GainItem(GainSelection.GRAVITY, defaultValue);
    }
}
