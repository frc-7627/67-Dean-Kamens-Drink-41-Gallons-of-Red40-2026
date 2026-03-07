package frc.robot.subsystems.controllable.launcher;

import static frc.robot.Constants.LauncherConstants.CLOSE_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_MAP;
import static frc.robot.Constants.LauncherConstants.FAR_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_MAP;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public enum LauncherDomain {
    CLOSE_ZONE(CLOSE_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_MAP),
    FAR_ZONE(FAR_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_MAP);

    public final InterpolatingDoubleTreeMap distanceFeetToMotorFPSMap;

    private LauncherDomain(InterpolatingDoubleTreeMap distanceFeetToMotorFPSMap) {
        this.distanceFeetToMotorFPSMap = distanceFeetToMotorFPSMap;
    }
}
