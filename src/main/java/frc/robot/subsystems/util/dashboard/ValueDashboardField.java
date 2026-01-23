package frc.robot.subsystems.util.dashboard;

public abstract class ValueDashboardField<Inner> extends BaseDashboardField<Inner> {
    private final Inner initialValue;

    protected ValueDashboardField(String subsystemName, String fieldName, Inner initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode);

        this.initialValue = initialValue;
    }

    @Override
    protected final Inner getDefaultValue() {
        return initialValue;
    }
}
