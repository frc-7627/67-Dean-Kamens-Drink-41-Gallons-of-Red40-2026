package frc.bofalib.dashboard;

import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.function.BooleanConsumer;
import frc.bofalib.gains.GainItem;
import frc.bofalib.gains.Gains;
import frc.bofalib.util.FunctionalUtil;

public final class DashboardItems {
    private static String convertToTopicName(String key) {
        return "/SmartDashboard/" + key;
    }

    public static DoubleConsumer createDoublePusher(String key) {
        return NetworkTableInstance.getDefault()
            .getDoubleTopic(convertToTopicName(key)).publish();
    }

    public static BooleanConsumer createBooleanPusher(String key) {
        return NetworkTableInstance.getDefault()
            .getBooleanTopic(convertToTopicName(key)).publish();
    }

    public static DoubleSupplier createDoublePuller(
        String key, 
        double defaultValue
    ) {
        final DoubleTopic topic = NetworkTableInstance.getDefault()
            .getDoubleTopic(convertToTopicName(key));

        try (DoublePublisher pub = topic.publish()) {
            pub.set(defaultValue);
        }
        
        return topic.subscribe(defaultValue);
    }

    public static DoubleSupplier createCheckedDoublePuller(
        String key, 
        double defaultValue, 
        DoublePredicate predicate
    ) {
        if (!predicate.test(defaultValue)) {
            throw new BadInitialValueError(defaultValue);
        }

        final DoubleTopic topic = NetworkTableInstance.getDefault()
            .getDoubleTopic(convertToTopicName(key));

        try (DoublePublisher pub = topic.publish()) {
            pub.set(defaultValue);
        }

        return new DoubleSupplier() {
            private final DoubleSubscriber sub = topic.subscribe(defaultValue);

            private double currentValue = defaultValue;

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
        final BooleanTopic topic = NetworkTableInstance.getDefault()
            .getBooleanTopic(convertToTopicName(key));

        try (BooleanPublisher pub = topic.publish()) {
            pub.set(defaultValue);
        }
        
        return topic.subscribe(defaultValue);
    }

    public static Runnable createGainsDashboard(
        KeyBuilder keyBuilder,
        Gains gains,
        List<GainItem> gainItems 
    ) {
        return FunctionalUtil.flattenRunnables(
            gainItems.stream().map(
                gainItem -> {
                    Objects.requireNonNull(gainItem);

                    return FunctionalUtil.composeConditional(
                        value -> gains.setGain(gainItem.selection, value), 
                        createDoublePuller(
                            keyBuilder.copyExtendedToString(gainItem.selection.name), 
                            gainItem.defaultValue
                        ),
                        FunctionalUtil.hasChangedDoublePredicate()
                    );
                }
            ).toList()
        );
    }
}
