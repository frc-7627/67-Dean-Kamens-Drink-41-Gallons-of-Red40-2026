package frc.robot.subsystems.vision;

import static frc.robot.Constants.VisionConstants.*;

public class StandardDeviationsSubdashboard extends SubdashboardBase {
    private final PullingDouble singleTagStdDev;
    private final PullingDouble multiTagStdDev;
    private final StandardDeviations workingStandardDeviations = new StandardDeviations();

    StandardDeviationsSubdashboard(String dashboardName) {
        super(dashboardName);

        this.singleTagStdDev = new PullingDouble(dashboardName, "Single Tag Standard Deviation",
                DEFAULT_SINGLE_TAG_STD_DEV);

        this.multiTagStdDev = new PullingDouble(dashboardName, "Multiple Tag Standard Deviation",
                DEFAULT_MULTI_TAG_STD_DEV);
    }

    public StandardDeviations get() {
        workingStandardDeviations.setSingleTagStdDev(singleTagStdDev.getPulled());
        workingStandardDeviations.setMultiTagStdDev(multiTagStdDev.getPulled());
        return workingStandardDeviations;
    }
}
