package frc.robot.subsystems.launcher.dashboard;

import static frc.robot.Constants.LauncherConstants.DEFAULT_RAMP_UP_PERIOD;

import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.launcher.util.MotorsConfigurator;
import frc.robot.subsystems.util.dashboard.DashboardDouble;
import frc.robot.subsystems.util.dashboard.FieldMode;

public class RampUpPeriod extends DashboardDouble {
    private static final String FIELD_NAME = "Ramp Up Period";
    private final MotorsConfigurator configurator;

    public RampUpPeriod(MotorsConfigurator configurator) {
        super(Launcher.class.getSimpleName(), FIELD_NAME, DEFAULT_RAMP_UP_PERIOD, FieldMode.PULL);

        this.configurator = configurator;
    }

    @Override
    public void setInnerValue(Double innerValue) {
        super.setInnerValue(innerValue);

        configurator.applyRampUpPeriod(getInnerValue());
    }
}
