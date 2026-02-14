package frc.bofalib.generic.hardware.tof.pwf;

import java.util.function.BooleanSupplier;

import frc.bofalib.query.BooleanQueryable;

public final class PWFTimeOfFlight implements BooleanQueryable<PWFTimeOfFlightQuery> {
    // TODO: import fusion and uncomment
    // private final TimeOfFlight timeOfFlight;
    
    public PWFTimeOfFlight(int deviceId) {
        // TODO: import fusion and uncomment
        // this.timeOfFlight = new TimeOfFlight(deviceId);
    }

    private boolean checkQuery(PWFTimeOfFlightQuery query) {
        // TODO: import fusion and uncomment
        // final double range = timeOfFlight.getRange();

        return switch (query.side()) {
            // TODO: import fusion and replace placeholder
            case LESSER -> /* query.value() < range */ false;
            // TODO: import fusion and replace placeholder
            case GREATER -> /* query.value() > range */ false;
        };
    }

    @Override
    public BooleanSupplier queryBoolean(PWFTimeOfFlightQuery query) {
        // TODO: import fusion and uncomment
        return () -> /* timeOfFlight.isRangeValid() && */ checkQuery(query);
    }
}
