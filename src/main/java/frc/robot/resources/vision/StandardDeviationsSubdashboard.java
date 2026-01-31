package frc.robot.resources.vision;

import frc.robotlib.resource.dashboard.SharedSubdashboard;
import frc.robotlib.resource.dashboard.fields.PullingDouble;
import frc.robotlib.resource.dashboard.fields.SubdashboardBase;
import static frc.robot.Constants.VisionConstants.*;

public class StandardDeviationsSubdashboard extends SubdashboardBase implements SharedSubdashboard {
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
