package frc.robot.subsystems.swervedrive.vision.dashboard;

import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.vision.Vision;
import frc.robot.subsystems.util.dashboard.DashboardDouble;
import frc.robot.subsystems.util.dashboard.DashboardField;
import frc.robot.subsystems.util.dashboard.FieldMode;
import static frc.robot.Constants.VisionConstants.*;

public class StandardDeviations implements DashboardField {
    private static final String SUBSYSTEM_NAME = String.format("%s/%s",
            SwerveSubsystem.class.getSimpleName(), Vision.class.getSimpleName());

    private final DashboardDouble singleTagStdDev = new DashboardDouble(SUBSYSTEM_NAME,
            "Single Tag Standard Deviation", DEFAULT_SINGLE_TAG_STD_DEV, FieldMode.PULL);

    private final DashboardDouble multiTagStdDev = new DashboardDouble(SUBSYSTEM_NAME,
            "Multiple Tag Standard Deviation", DEFAULT_MULTI_TAG_STD_DEV, FieldMode.PULL);

    @Override
    public void init() {
        singleTagStdDev.init();
        multiTagStdDev.init();
    }

    @Override
    public void update() {
        singleTagStdDev.update();
        multiTagStdDev.update();
    }

    public double getSingleTagStdDev() {
        return singleTagStdDev.getInnerValue();
    }

    public double getMultiTagStdDev() {
        return multiTagStdDev.getInnerValue();
    }

    public void setSingleTagStdDev(double newSingleTagStdDev) {
        singleTagStdDev.setInnerValue(newSingleTagStdDev);
    }

    public void setMultiTagStdDev(double newMultiTagStdDev) {
        multiTagStdDev.setInnerValue(newMultiTagStdDev);
    }
}
