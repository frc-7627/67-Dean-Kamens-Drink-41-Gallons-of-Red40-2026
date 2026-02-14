package frc.bofalib.generic.hardware.tof.pwf;

public record PWFTimeOfFlightQuery(double value, Side side) {
    public static enum Side {
        LESSER,
        GREATER;
    }
}
