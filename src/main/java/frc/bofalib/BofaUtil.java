package frc.bofalib;

import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

public final class BofaUtil {
    public static String mockName(String name) {
        return name + " (mock)";
    }

    /**
     * @param gravityMPS2 gravitational acceleration in meters * sec^-2
     * @param horizontalM horizontal distance in meters
     * @param verticalM vertical distance in meters
     * @param pitchRad pitch angle in radians
     * @return the initial velocity required for the ballistic arc
     */
    public static double computeInitialVelocityMPS(
        double gravityMPS2,
        double horizontalM,
        double verticalM,
        double pitchRad
    ) {
        return Math.sqrt(
            0.5 * gravityMPS2 / (verticalM - horizontalM * Math.tan(pitchRad))
        ) * horizontalM / Math.cos(pitchRad);
    }

    /**
     * @param idealVelocityMPS ideal velocity in meters * sec^-1
     * @param efficiency energy efficiency, a dimensionless number in the range (0, 1]
     * @return actual velocity in meters * sec^-1
     */
    public static double computeActualVelocityMPS(
        double idealVelocityMPS,
        double efficiency
    ) {
        return Math.sqrt(
            idealVelocityMPS * idealVelocityMPS / efficiency
        );
    }

    /**
     * @param horizontalM horizontal distance in meters
     * @param verticalM vertical distance in meters
     * @param pitchRad pitch angle in radians
     * @return whether the ballistic arc is possible
     */
    public static boolean isBallisticPossible(
        double horizontalM,
        double verticalM,
        double pitchRad
    ) {
        return horizontalM * Math.tan(pitchRad) - verticalM > 0;
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

    public static DoubleSupplier negativeSupplier(DoubleSupplier supplier) {
        return () -> -supplier.getAsDouble();
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

    public static Runnable composeConditional(
        DoubleConsumer consumer, 
        DoubleSupplier supplier, 
        DoublePredicate predicate
    ) {
        return () -> {
            final double value = supplier.getAsDouble();

            if (predicate.test(value)) {
                consumer.accept(value);
            }
        };
    }

    public static DoublePredicate hasChangedDoublePredicate() {
        return new DoublePredicate() {
            private double currentValue = Double.NaN;

            @Override
            public boolean test(double value) {
                if (value == currentValue) {
                    return false;
                }

                currentValue = value;
                return true;
            }
        };
    }
}
