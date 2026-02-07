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
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.util.function.BooleanConsumer;

public final class DashboardItems {
    private static final Frequency PUSH_FREQUENCY = Hertz.of(10);

    private static final NetworkTable DASHBOARD_TABLE = NetworkTableInstance
        .getDefault().getTable("SmartDashboard");

    public static DoubleConsumer createDoublePusher(String key) {
        return new DoubleConsumer() {
            private final DoublePublisher pub = DASHBOARD_TABLE
                .getDoubleTopic(key).publish();

            @Override
            public void accept(double value) {
                pub.set(value);
            }
        };
    }

    public static BooleanConsumer createBooleanPusher(String key) {
        return new BooleanConsumer() {
            private final BooleanPublisher pub = DASHBOARD_TABLE
                .getBooleanTopic(key).publish();

            @Override
            public void accept(boolean value) {
                pub.set(value);
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

                final DoubleTopic topic = DASHBOARD_TABLE
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
                final BooleanTopic topic = DASHBOARD_TABLE
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
