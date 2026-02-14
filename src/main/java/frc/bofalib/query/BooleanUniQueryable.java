package frc.bofalib.query;

import java.util.function.BooleanSupplier;

public interface BooleanUniQueryable<
    FirstQuery, 
    Query extends UniQuery<FirstQuery>
> extends BooleanQueryable<Query> {
    BooleanQueryable<FirstQuery> getFirstQueryable();

    @Override
    default BooleanSupplier queryBoolean(Query query) {
        return getFirstQueryable().queryBoolean(query.getFirstQuery());
    }
}
