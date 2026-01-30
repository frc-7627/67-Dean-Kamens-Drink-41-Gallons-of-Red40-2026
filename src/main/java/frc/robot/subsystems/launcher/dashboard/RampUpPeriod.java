package frc.robot.subsystems.launcher.dashboard;

import static edu.wpi.first.units.Units.Milliseconds;
import static frc.robot.Constants.LauncherConstants.DEFAULT_RAMP_UP_PERIOD;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.launcher.util.MotorsConfigurator;
import frc.robot.subsystems.util.dashboard.DashboardDouble;
import frc.robot.subsystems.util.dashboard.FieldMode;

public class RampUpPeriod extends DashboardDouble {
    private static final String FIELD_NAME = "Ramp Up Period";
    private final MotorsConfigurator configurator;
    private final Timer timer = new Timer();

    public RampUpPeriod(MotorsConfigurator configurator) {
        super(Launcher.class.getSimpleName(), FIELD_NAME, DEFAULT_RAMP_UP_PERIOD, FieldMode.PULL);

        this.configurator = configurator;

        timer.start();
    }

    @Override
    public void setInnerValue(Double innerValue) {
        super.setInnerValue(innerValue);

        if (timer.hasElapsed(Milliseconds.of(15))) { // TODO create constant
            configurator.applyRampUpPeriod(getInnerValue());

            timer.restart();
        }
    }
}
