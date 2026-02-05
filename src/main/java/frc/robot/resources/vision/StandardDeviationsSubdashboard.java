package frc.robot.resources.vision;

import static frc.robot.Constants.VisionConstants.*;
import frc.bofalib.dashboard.fields.PullingDouble;
import frc.bofalib.dashboard.fields.SubdashboardBase;

public class StandardDeviationsSubdashboard extends SubdashboardBase {
    private final PullingDouble singleTagStdDev;
    private final PullingDouble multiTagStdDev;

    StandardDeviationsSubdashboard(String dashboardName) {
        super(dashboardName);

        this.singleTagStdDev = new PullingDouble(dashboardName, "Single Tag Standard Deviation",
                DEFAULT_SINGLE_TAG_STD_DEV);

        this.multiTagStdDev = new PullingDouble(dashboardName, "Multiple Tag Standard Deviation",
                DEFAULT_MULTI_TAG_STD_DEV);

        addSubresource(singleTagStdDev);
        addSubresource(multiTagStdDev);
    }

    public StandardDeviations get() {
        return new StandardDeviations(singleTagStdDev.getPulled(), multiTagStdDev.getPulled());
    }
}
