package frc.bofalib.generic.hardware.motor;

import edu.wpi.first.units.measure.AngularVelocity;

public non-sealed abstract class MotorVelocity implements MotorSetting {
    private AngularVelocity angularVelocity;

    public MotorVelocity(AngularVelocity angularVelocity) {
        this.angularVelocity = angularVelocity.mutableCopy();
    }

    public AngularVelocity getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(AngularVelocity angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
}
