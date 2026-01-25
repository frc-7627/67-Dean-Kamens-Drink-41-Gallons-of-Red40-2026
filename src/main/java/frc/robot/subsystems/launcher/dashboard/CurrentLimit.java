package frc.robot.subsystems.launcher.dashboard;

import static frc.robot.Constants.LauncherConstants.DEFAULT_CURRENT_LIMIT;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.launcher.util.MotorsConfigurator;
import frc.robot.subsystems.util.dashboard.DashboardDouble;
import frc.robot.subsystems.util.dashboard.FieldMode;

public class CurrentLimit extends DashboardDouble {
    private static final String FIELD_NAME = "Current Limit";
    private final MotorsConfigurator configurator;

    public CurrentLimit(MotorsConfigurator configurator) {
        super(Launcher.class.getSimpleName(), FIELD_NAME, DEFAULT_CURRENT_LIMIT, FieldMode.PULL);

        this.configurator = configurator;
    }

    @Override
    public void setInnerValue(Double innerValue) {
        super.setInnerValue(innerValue);

        configurator.applyCurrentLimit(getInnerValue());
    }
}
