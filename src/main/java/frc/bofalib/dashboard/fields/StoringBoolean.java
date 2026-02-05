package frc.bofalib.dashboard.fields;

import java.util.function.Consumer;

public class StoringBoolean extends StoringFieldBase<Boolean> {
    public StoringBoolean(String superdashboardName, String fieldName, Consumer<Boolean> onStore,
            Boolean initialBoolean) {
        super(superdashboardName, fieldName, Transport::createBoolean, stored -> true, onStore,
                initialBoolean);
    }

    public StoringBoolean(String superdashboardName, String fieldName, Boolean initialBoolean) {
        this(superdashboardName, fieldName, stored -> {
        }, initialBoolean);
    }
}
