package frc.bofalib.query;

public interface DoubleUniQueryable<
    FirstQuery,
    Query extends UniQuery<FirstQuery>
> extends DoubleQueryable<Query> {
    DoubleQueryable<FirstQuery> getFirstQueryable();

    @Override
    default double queryDouble(Query query) {
        return getFirstQueryable().queryDouble(query.getFirstQuery());
    }
}
