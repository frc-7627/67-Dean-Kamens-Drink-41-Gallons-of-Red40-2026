package frc.bofalib.query;

public interface BooleanUniQueryable<
    FirstQuery, 
    Query extends UniQuery<FirstQuery>
> extends BooleanQueryable<Query> {
    BooleanQueryable<FirstQuery> getFirstQueryable();

    @Override
    default boolean queryBoolean(Query query) {
        return getFirstQueryable().queryBoolean(query.getFirstQuery());
    }
}
