package frc.bofalib.query;

import java.util.function.DoubleSupplier;

public interface DoubleQueryable<DoubleQuery> {
    DoubleSupplier queryDouble(DoubleQuery query);
}
