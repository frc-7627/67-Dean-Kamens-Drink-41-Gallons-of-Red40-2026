package frc.robot.subsystems.launcher.dashboard;

import static frc.robot.Constants.LauncherConstants.DEFAULT_SHOOT_SPEED;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.launcher.util.MotorsConfigurator;
import frc.robot.subsystems.util.dashboard.MotorSpeed;

public class ShootSpeed extends MotorSpeed {
    private static final String FIELD_NAME = "Shoot Speed";
    private final MotorsConfigurator configurator;
    

    public ShootSpeed(MotorsConfigurator configurator) {
        super(Launcher.class.getSimpleName(), FIELD_NAME, DEFAULT_SHOOT_SPEED);

        this.configurator = configurator;
    }

    @Override
    public void setInnerValue(Double innerValue) {
        super.setInnerValue(innerValue);

        configurator.applyShootSpeed(getInnerValue());
    }
}
