package frc.robotlib.dashboard.fields;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class TransportDouble extends TransportBase<Double> {
    TransportDouble(String key) {
        super(key);
    }

    @Override
    public Double pull(Double currentValue) {
        return SmartDashboard.getNumber(getKey(), currentValue);
    }

    @Override
    public void push(Double value) {
        SmartDashboard.putNumber(getKey(), value);
    }
}
