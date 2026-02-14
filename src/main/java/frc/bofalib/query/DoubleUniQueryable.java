package frc.bofalib.query;

import java.util.function.DoubleSupplier;

public interface DoubleUniQueryable<
    FirstQuery,
    Query extends UniQuery<FirstQuery>
> extends DoubleQueryable<Query> {
    DoubleQueryable<FirstQuery> getFirstQueryable();

    @Override
    default DoubleSupplier queryDouble(Query query) {
        return getFirstQueryable().queryDouble(query.getFirstQuery());
    }
}
