package frc.robot.subsystems.vision;

import static frc.robot.Constants.VisionConstants.*;
import java.util.function.DoubleSupplier;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;

public class StandardDeviationsSubdashboard {
    private final DoubleSupplier singleTagStdDevSupplier;
    private final DoubleSupplier multiTagStdDevSupplier;
    private final StandardDeviations workingStandardDeviations = new StandardDeviations();

    StandardDeviationsSubdashboard(KeyBuilder rootKeyBuilder) {
        this.singleTagStdDevSupplier = DashboardItems.createDoublePuller(
            rootKeyBuilder.copyExtendedToString("Single Tag Standard Deviation"), 
            DEFAULT_SINGLE_TAG_STD_DEV
        );
        this.multiTagStdDevSupplier = DashboardItems.createDoublePuller(
            rootKeyBuilder.copyExtendedToString("Multi Tag Standard Deviation"), 
            DEFAULT_MULTI_TAG_STD_DEV
        );
    }

    public StandardDeviations get() {
        workingStandardDeviations.setSingleTagStdDev(singleTagStdDevSupplier.getAsDouble());
        workingStandardDeviations.setMultiTagStdDev(multiTagStdDevSupplier.getAsDouble());
        return workingStandardDeviations;
    }
}
