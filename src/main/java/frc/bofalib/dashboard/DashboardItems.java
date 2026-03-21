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
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.bofalib.gains.GainItem;
import frc.bofalib.gains.Gains;
import frc.bofalib.util.FunctionalUtil;

public final class DashboardItems {
    private static String convertToTopicName(String key) {
        return "/SmartDashboard/" + key;
    }

    public static DoubleConsumer createDoublePusher(String key, boolean retained) {
        final DoubleTopic topic = NetworkTableInstance.getDefault()
            .getDoubleTopic(convertToTopicName(key));

        topic.setRetained(retained);

        final DoubleEntry entry = topic.getEntry(0);

        entry.set(0);

        return entry;
    }

    public static BooleanConsumer createBooleanPusher(String key, boolean retained) {
        final BooleanTopic topic = NetworkTableInstance.getDefault()
            .getBooleanTopic(convertToTopicName(key));

        topic.setRetained(retained);

        final BooleanEntry entry = topic.getEntry(false);

        entry.set(false);

        return entry;
    }

    public static DoubleSupplier createDoublePuller(
        String key, 
        boolean retained,
        double defaultValue
    ) {
        final DoubleTopic topic = NetworkTableInstance.getDefault()
            .getDoubleTopic(convertToTopicName(key));

        topic.setRetained(retained);

        final DoubleEntry entry = topic.getEntry(defaultValue);

        return entry;
    }

    public static DoubleSupplier createCheckedDoublePuller(
        String key, 
        boolean retained,
        double defaultValue, 
        DoublePredicate predicate
    ) {
        if (!predicate.test(defaultValue)) {
            throw new BadInitialValueError(defaultValue);
        }

        final DoubleTopic topic = NetworkTableInstance.getDefault()
            .getDoubleTopic(convertToTopicName(key));

        topic.setRetained(retained);

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
        boolean retained,
        boolean defaultValue
    ) {
        final BooleanTopic topic = NetworkTableInstance.getDefault()
            .getBooleanTopic(convertToTopicName(key));

        topic.setRetained(retained);

        final BooleanEntry entry = topic.getEntry(defaultValue);
        
        entry.set(defaultValue);

        return entry;
    }

    public static Runnable createGainsDashboard(
        KeyBuilder keyBuilder,
        boolean retained,
        Gains gains,
        List<GainItem> gainItems,
        BooleanSupplier isActiveSupplier
    ) {
        return FunctionalUtil.flattenRunnables(
            gainItems.stream().map(
                gainItem -> {
                    Objects.requireNonNull(gainItem);

                    return FunctionalUtil.composeConditional(
                        value -> gains.setGain(gainItem.selection, value), 
                        createDoublePuller(
                            keyBuilder.copyExtendedToString(gainItem.selection.name), 
                            retained,
                            gainItem.defaultValue
                        ),
                        FunctionalUtil.hasChangedDoublePredicate()
                            .and(x -> isActiveSupplier.getAsBoolean())
                    );
                }
            ).toList()
        );
    }

    public static void send(String key, Sendable value) {
        SmartDashboard.putData(convertToTopicName(key), value);
    }
}
