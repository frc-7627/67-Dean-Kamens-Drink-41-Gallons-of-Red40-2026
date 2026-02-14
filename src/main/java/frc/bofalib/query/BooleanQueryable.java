package frc.bofalib.query;

import java.util.function.BooleanSupplier;

public interface BooleanQueryable<BooleanQuery> {
    BooleanSupplier queryBoolean(BooleanQuery query);
}
