package frc.bofalib.dashboard;

import static edu.wpi.first.units.Units.Hertz;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.util.function.BooleanConsumer;
import frc.bofalib.Util;

public final class DashboardItems {
    static final Frequency PUSH_FREQUENCY = Hertz.of(10);

    public static DoubleConsumer createDoublePusher(String key) {
        return new DoubleConsumer() {
            private final DoublePublisher pub = DashboardUtil.getDashboardTable()
                .getDoubleTopic(key).publish();

            @Override
            public void accept(double value) {
                Util.throttle(() -> pub.set(value), PUSH_FREQUENCY);
            }
        };
    }

    public static BooleanConsumer createBooleanPusher(String key) {
        return new BooleanConsumer() {
            private final BooleanPublisher pub = DashboardUtil.getDashboardTable()
                .getBooleanTopic(key).publish();

            @Override
            public void accept(boolean value) {
                Util.throttle(() -> pub.set(value), PUSH_FREQUENCY);
            }
        };
    }

    public static DoubleSupplier createDoublePuller(
        String key, 
        double defaultValue
    ) {
        return createDoublePuller(key, defaultValue, value -> true);
    }

    public static DoubleSupplier createDoublePuller(
        String key, 
        double defaultValue, 
        DoublePredicate predicate
    ) {
        return new DoubleSupplier() {
            private final DoubleSubscriber sub;

            private double currentValue = defaultValue;

            {
                if (!predicate.test(defaultValue)) {
                    throw new BadInitialValueError(defaultValue);
                }

                final DoubleTopic topic = DashboardUtil.getDashboardTable()
                    .getDoubleTopic(key);

                if (!topic.exists()) {
                    try (DoublePublisher pub = topic.publish()) {
                        pub.set(defaultValue);
                    }
                }

                this.sub = topic.subscribe(defaultValue);
            }

            @Override
            public double getAsDouble() {
                final double newValue = sub.get();

                if (predicate.test(newValue)) {
                    currentValue = newValue;
                } else {
                    try (DoublePublisher pub = sub.getTopic().publish()) {
                        pub.set(currentValue);
                    }
                }

                return currentValue;
            }
        };
    }

    public static BooleanSupplier createBooleanPuller(
        String key, 
        boolean defaultValue
    ) {
        return new BooleanSupplier() {
            private final BooleanSubscriber sub;

            {
                final BooleanTopic topic = DashboardUtil.getDashboardTable()
                    .getBooleanTopic(key);

                if (!topic.exists()) {
                    try (BooleanPublisher pub = topic.publish()) {
                        pub.set(defaultValue);
                    }
                }

                this.sub = topic.subscribe(defaultValue);
            }

            @Override
            public boolean getAsBoolean() {
                return sub.get();
            }
        };
    }
}
