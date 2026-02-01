package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.controller.PIDController;
import frc.robotlib.dashboard.fields.PullingDouble;
import frc.robotlib.dashboard.fields.SubdashboardBase;

final class AngularControlSubdashboard extends SubdashboardBase {
    private final PullingDouble kp;
    private final PullingDouble ki;
    private final PullingDouble kd;

    AngularControlSubdashboard(String superdashboardName) {
        super(superdashboardName, "Rotation Control Constants");

        this.kp = new PullingDouble(getKeyName(), "P", 5.0);
        this.ki = new PullingDouble(getKeyName(), "I", 1.0);
        this.kd = new PullingDouble(getKeyName(), "D", 1.0);
    }

    PIDController getController() {
        return new PIDController(getKp(), getKi(), getKd());
    }

    private double getKp() {
        return kp.getPulled();
    }

    private double getKi() {
        return ki.getPulled();
    }

    private double getKd() {
        return kd.getPulled();
    }
}
