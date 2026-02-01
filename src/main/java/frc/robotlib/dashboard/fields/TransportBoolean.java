package frc.robotlib.dashboard.fields;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class TransportBoolean extends TransportBase<Boolean> {
    TransportBoolean(String key) {
        super(key);
    }

    @Override
    public Boolean pull(Boolean currentValue) {
        return SmartDashboard.getBoolean(getKey(), currentValue);
    }

    @Override
    public void push(Boolean value) {
        SmartDashboard.putBoolean(getKey(), value);
    }
}
