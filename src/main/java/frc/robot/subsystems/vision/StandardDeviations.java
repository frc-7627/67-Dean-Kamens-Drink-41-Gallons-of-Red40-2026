package frc.robot.subsystems.vision;

import static frc.robot.Constants.VisionConstants.*;
import java.util.function.DoubleSupplier;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;

final class StandardDeviations {
    private final DoubleSupplier singleTagStdDevSupplier;
    private final DoubleSupplier multiTagStdDevSupplier;

    StandardDeviations(KeyBuilder rootKeyBuilder) {
        this.singleTagStdDevSupplier = DashboardItems.createDoublePuller(
            rootKeyBuilder.copyExtendedToString("Single Tag Standard Deviation"), 
            DEFAULT_SINGLE_TAG_STD_DEV
        );
        this.multiTagStdDevSupplier = DashboardItems.createDoublePuller(
            rootKeyBuilder.copyExtendedToString("Multi Tag Standard Deviation"), 
            DEFAULT_MULTI_TAG_STD_DEV
        );
    }

    double getSingleTagStdDev() {
        return singleTagStdDevSupplier.getAsDouble();
    }

    double getMultiTagStdDev() {
        return multiTagStdDevSupplier.getAsDouble();
    }
}
