package frc.robotlib.dashboard.fields;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class StoringDouble extends StoringFieldBase<Double> {
    public StoringDouble(String superdashboardName, String fieldName, Predicate<Double> checkStored,
            Consumer<Double> onStore, Double initialDouble) {
        super(superdashboardName, fieldName, Transport::createDouble, checkStored, onStore,
                initialDouble);
    }

    public StoringDouble(String superdashboardName, String fieldName, Consumer<Double> onStore,
            Double initialDouble) {
        this(superdashboardName, fieldName, stored -> true, onStore, initialDouble);
    }

    public StoringDouble(String superdashboardName, String fieldName, Predicate<Double> checkStored,
            Double initialDouble) {
        this(superdashboardName, fieldName, checkStored, stored -> {
        }, initialDouble);
    }

    public StoringDouble(String superdashboardName, String fieldName, Double initialDouble) {
        this(superdashboardName, fieldName, stored -> true, stored -> {
        }, initialDouble);
    }
}
