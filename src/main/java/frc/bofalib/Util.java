package frc.bofalib;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.util.function.BooleanConsumer;
import edu.wpi.first.wpilibj.Timer;

public final class Util {
    public static String mockName(String name) {
        return name + " (mock)";
    }

    public static DoubleSupplier addConsumer(DoubleSupplier supplier, DoubleConsumer consumer) {
        return () -> {
            final double value = supplier.getAsDouble();
            consumer.accept(value);
            return value;
        };
    }

    public static BooleanSupplier addConsumer(BooleanSupplier supplier, BooleanConsumer consumer) {
        return () -> {
            final boolean value = supplier.getAsBoolean();
            consumer.accept(value);
            return value;
        };
    }

    public static <Value> Supplier<Value> addConsumer(Supplier<Value> supplier, Consumer<Value> consumer) {
        return () -> {
            final Value value = supplier.get();
            consumer.accept(value);
            return value;
        };
    }

    public static Runnable throttle(Runnable action, Frequency frequency) {
        return throttle(action, frequency.asPeriod());
    }

    public static Runnable throttle(Runnable action, Time period) {
        return new Runnable() {
            private final Timer timer = new Timer();

            {
                timer.start();
            }

            @Override
            public void run() {
                if (timer.hasElapsed(period)) {
                    action.run();
                    timer.reset();
                }
            }
        };
    }
}
