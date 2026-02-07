package frc.bofalib;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

public final class Util {
    public static String mockName(String name) {
        return name + " (mock)";
    }

    /**
     * @param consumer the consumer
     * @return an operator that applies the consumer to its given value and returns the value
     */
    public static DoubleUnaryOperator returning(DoubleConsumer consumer) {
        return value -> {
            consumer.accept(value);
            return value;
        };
    }

    /**
     * @param consumer the consumer
     * @param supplier the supplier
     * @return their composition
     */
    public static Runnable compose(DoubleConsumer consumer, DoubleSupplier supplier) {
        return () -> consumer.accept(supplier.getAsDouble());
    }

    /**
     * @param consumer the consumer
     * @param supplier the supplier
     * @return their composition, that returns the supplied value
     */
    public static DoubleSupplier composeReturning(DoubleConsumer consumer, DoubleSupplier supplier) {
        return () -> returning(consumer)
            .applyAsDouble(supplier.getAsDouble());
    }
}
