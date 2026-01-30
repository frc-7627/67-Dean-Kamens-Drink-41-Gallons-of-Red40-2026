package frc.robot.subsystems.launcher.dashboard;

import static edu.wpi.first.units.Units.Milliseconds;

import static frc.robot.Constants.LauncherConstants.DEFAULT_CURRENT_LIMIT;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.launcher.util.MotorsConfigurator;
import frc.robot.subsystems.util.dashboard.DashboardDouble;
import frc.robot.subsystems.util.dashboard.FieldMode;

public class CurrentLimit extends DashboardDouble {
    private static final String FIELD_NAME = "Current Limit";
    private final MotorsConfigurator configurator;
    private final Timer timer = new Timer();

    public CurrentLimit(MotorsConfigurator configurator) {
        super(Launcher.class.getSimpleName(), FIELD_NAME, DEFAULT_CURRENT_LIMIT, FieldMode.PULL);

        this.configurator = configurator;

        timer.start();
    }

    @Override
    public void setInnerValue(Double innerValue) {
        super.setInnerValue(innerValue);

        if (timer.hasElapsed(Milliseconds.of(15))) { // TODO create constant
            configurator.applyCurrentLimit(getInnerValue());

            timer.reset();
        }
    }
}
