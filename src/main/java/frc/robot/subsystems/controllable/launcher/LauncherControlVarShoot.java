package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import java.util.function.Function;
import static frc.robot.Constants.LauncherConstants.*;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.robot.subsystems.controllable.drivebase.DistanceTargetter;

final class LauncherControlVarShoot implements LauncherControl {
    private final Function<LauncherImpl, TalonFXBatchControl> controlFunction;

    LauncherControlVarShoot(DistanceTargetter targetter) {
        this.controlFunction = impl -> impl.motors.getSetVelocityControl(
            () -> DISTANCE_FEET_TO_MOTOR_RPS_MAP.get(targetter.getTargetMeters()),
            RotationsPerSecond
        );
    }

    @Override
    public String getLoggableName() {
        return "Launcher Shoot Variable";
    }

    @Override
    public TalonFXBatchControl getFirstControl(LauncherImpl target) {
        return controlFunction.apply(target);
    }
}
