package frc.robot.subsystems.drivebase;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static frc.robot.Constants.DrivebaseConstants.*;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Timer;
import frc.robotlib.dashboard.fields.PullingDouble;

public final class AngularControl {
    private final PullingDouble kp;
    private final PullingDouble ki;
    private final PullingDouble kd;
    private final PIDController controller;
    private final KinematicSupplier kinematicSupplier;
    private final Timer convergenceTimer = new Timer();

    AngularControl(String superdashboardName, KinematicSupplier kinematicSupplier) {
        this.controller = new PIDController(0, 0, 0);
        this.kinematicSupplier = kinematicSupplier;

        final String key = superdashboardName + "/Angular Control";

        this.kp = new PullingDouble(key, "P", this::updateKp, 5.0);
        this.ki = new PullingDouble(key, "I", this::updateKi, 1.0);
        this.kd = new PullingDouble(key, "D", this::updateKd, 1.0);
    }

    private void updateKp(double kp) {
        controller.setP(kp);
    }

    private void updateKi(double ki) {
        controller.setI(ki);
    }

    private void updateKd(double kd) {
        controller.setD(kd);
    }

    private Rotation2d getCurrentOrientation() {
        return kinematicSupplier.getPose().getRotation();
    }

    private AngularVelocity getCurrentRotationRate() {
        return RadiansPerSecond.of(kinematicSupplier.getSpeeds().omegaRadiansPerSecond);
    }
    
    public AngularVelocity getRotationRate(Angle targetOrientation) {
        return RadiansPerSecond.of(
            controller.calculate(
                getCurrentOrientation().getRadians(),
                targetOrientation.in(Radians)
            )
        );
    }

    private boolean hasConvergedImmediate(Angle targetOrientation) {
        return 
            getCurrentOrientation().getMeasure().isNear(targetOrientation, ANGULAR_EPSILON) &&
            getCurrentRotationRate().isNear(RadiansPerSecond.zero(), ANGULAR_VELOCITY_EPSILON);
    }

    public boolean hasConverged(Angle targetOrientation) {
        if (hasConvergedImmediate(targetOrientation)) {
            convergenceTimer.start();

            if (convergenceTimer.hasElapsed(CONVERGENCE_PERIOD)) {
                convergenceTimer.stop();
                convergenceTimer.reset();
                return true;
            }
        } else {
            convergenceTimer.stop();
            convergenceTimer.reset();
        }

        return false;
    }

    public void reset() {
        controller.reset();
    }
}
