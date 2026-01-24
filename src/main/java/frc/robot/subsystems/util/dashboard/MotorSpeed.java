package frc.robot.subsystems.util.dashboard;

public class MotorSpeed extends DashboardDouble {
    public MotorSpeed(String subsystemName, String fieldName, double initialValue) {
        super(subsystemName, fieldName, initialValue, FieldMode.PULL);
    }

    @Override
    protected boolean checkValue(Double value) {
        return -1.0 <= value && value <= 1.0;
    }
}
