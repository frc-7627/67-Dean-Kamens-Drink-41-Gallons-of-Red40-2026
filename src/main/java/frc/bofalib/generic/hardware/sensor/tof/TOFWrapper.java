package frc.bofalib.generic.hardware.sensor.tof;

import java.util.function.BooleanSupplier;

import frc.bofalib.query.BooleanQueryable;

public final class TOFWrapper implements BooleanQueryable<TOFQuery> {
    // TODO: import fusion and uncomment
    // private final TimeOfFlight timeOfFlight;
    
    public TOFWrapper(int deviceId) {
        // TODO: import fusion and uncomment
        // this.timeOfFlight = new TimeOfFlight(deviceId);
    }

    private boolean checkQuery(TOFQuery query) {
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
    public BooleanSupplier queryBoolean(TOFQuery query) {
        // TODO: import fusion and uncomment
        return () -> /* timeOfFlight.isRangeValid() && */ checkQuery(query);
    }
}
