package frc.robot.subsystems.util.dashboard;

public abstract class ObjectDashboardField<Inner> extends BaseDashboardField<Inner> {
    private final Inner defaultValue;

    protected ObjectDashboardField(String subsystemName, String fieldName, Inner initialValue) {
        super(subsystemName, fieldName, initialValue, FieldMode.PUSH);

        this.defaultValue = null;
    }

    protected ObjectDashboardField(String subsystemName, String fieldName, Inner initialValue, Inner defaultValue) {
        super(subsystemName, fieldName, initialValue, FieldMode.PULL);

        this.defaultValue = defaultValue;
    }

    @Override
    protected final Inner getDefaultValue() {
        if (!getFieldMode().isPull()) {
            throw new IllegalStateException("Default value only exists for pulling fields!");
        }
        return defaultValue;
    }
}
