package frc.robotlib.resource.dashboard.fields;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PullingDouble extends PullingFieldBase<Double> {
    public PullingDouble(String superdashboardName, String fieldName, Predicate<Double> checkPulled,
            Consumer<Double> onPull, Double initialDouble) {
        super(superdashboardName, fieldName, Transport::createDouble, checkPulled, onPull,
                initialDouble);
    }

    public PullingDouble(String superdashboardName, String fieldName, Consumer<Double> onPull,
            Double initialDouble) {
        this(superdashboardName, fieldName, pulled -> true, onPull, initialDouble);
    }

    public PullingDouble(String superdashboardName, String fieldName, Predicate<Double> checkPulled,
            Double initialDouble) {
        this(superdashboardName, fieldName, checkPulled, pulled -> {
        }, initialDouble);
    }

    public PullingDouble(String superdashboardName, String fieldName, Double initialDouble) {
        this(superdashboardName, fieldName, pulled -> true, pulled -> {
        }, initialDouble);
    }
}
