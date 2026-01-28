package frc.robot.subsystems.launcher.dashboard;

import static edu.wpi.first.units.Units.Milliseconds;

import static frc.robot.Constants.LauncherConstants.DEFAULT_SHOOT_SPEED;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.launcher.util.MotorsConfigurator;
import frc.robot.subsystems.util.dashboard.MotorSpeed;

public class ShootSpeed extends MotorSpeed {
    private static final String FIELD_NAME = "Shoot Speed";
    private final MotorsConfigurator configurator;
    private final Timer timer = new Timer();

    public ShootSpeed(MotorsConfigurator configurator) {
        super(Launcher.class.getSimpleName(), FIELD_NAME, DEFAULT_SHOOT_SPEED);

        this.configurator = configurator;

        timer.start();
    }

    @Override
    public void setInnerValue(Double innerValue) {
        super.setInnerValue(innerValue);

        if (timer.hasElapsed(Milliseconds.of(15))) {
            configurator.applyShootSpeed(getInnerValue());

            timer.restart();
        }
    }
}
