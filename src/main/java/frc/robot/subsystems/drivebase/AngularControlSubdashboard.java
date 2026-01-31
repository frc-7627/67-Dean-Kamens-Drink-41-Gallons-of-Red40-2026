package frc.robot.subsystems.drivebase;

import frc.robotlib.resource.dashboard.fields.PullingDouble;
import frc.robotlib.resource.dashboard.fields.SubdashboardBase;

final class AngularControlSubdashboard extends SubdashboardBase {
    private final PullingDouble kp;
    private final PullingDouble ki;
    private final PullingDouble kd;

    AngularControlSubdashboard(String superdashboardName) {
        super(superdashboardName, "Rotation Control Constants");

        this.kp = new PullingDouble(getKeyName(), "P", 5.0);
        this.ki = new PullingDouble(getKeyName(), "I", 0.0);
        this.kd = new PullingDouble(getKeyName(), "D", 0.0);
    }

    double getKp() {
        return kp.getPulled();
    }

    double getKi() {
        return ki.getPulled();
    }

    double getKd() {
        return kd.getPulled();
    }
}
