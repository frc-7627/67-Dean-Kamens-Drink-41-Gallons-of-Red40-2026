package frc.bofalib.generic.hardware.sensor.pwf;

public record PWFTimeOfFlightQuery(double value, Side side) {
    public static enum Side {
        LESSER,
        GREATER;
    }
}
