package frc.bofalib.dashboard;

import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.DoubleEntry;
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
        final DoubleTopic topic = NetworkTableInstance.getDefault()
            .getDoubleTopic(convertToTopicName(key));

        topic.setRetained(false);

        final DoubleEntry entry = topic.getEntry(0);

        entry.set(0);

        return entry;
    }

    public static BooleanConsumer createBooleanPusher(String key) {
        final BooleanTopic topic = NetworkTableInstance.getDefault()
            .getBooleanTopic(convertToTopicName(key));

        topic.setRetained(false);

        final BooleanEntry entry = topic.getEntry(false);

        entry.set(false);

        return entry;
    }

    public static DoubleSupplier createDoublePuller(
        String key, 
        double defaultValue
    ) {
        final DoubleTopic topic = NetworkTableInstance.getDefault()
            .getDoubleTopic(convertToTopicName(key));

        topic.setRetained(false);

        final DoubleEntry entry = topic.getEntry(defaultValue);

        return entry;
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

        topic.setRetained(false);

        final DoubleEntry entry = topic.getEntry(defaultValue);

        entry.set(defaultValue);

        return new DoubleSupplier() {
            private double currentValue = defaultValue;

            @Override
            public double getAsDouble() {
                final double newValue = entry.get();

                if (predicate.test(newValue)) {
                    currentValue = newValue;
                } else {
                    entry.set(defaultValue);
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

        topic.setRetained(false);

        final BooleanEntry entry = topic.getEntry(defaultValue);
        
        entry.set(defaultValue);

        return entry;
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
