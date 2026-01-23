package frc.robot.subsystems.util.dashboard;

public abstract class ValueDashboardField<Inner> extends BaseDashboardField<Inner> {
    private final Inner defaultValue;

    protected ValueDashboardField(String subsystemName, String fieldName, Inner initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode);

        this.defaultValue = fieldMode.isPull() ? initialValue : null;
    }

    @Override
    protected final Inner getDefaultValue() {
        if (!getFieldMode().isPull()) {
            throw new IllegalStateException("Default value only exists for pulling fields!");
        }
        return defaultValue;
    }
}
