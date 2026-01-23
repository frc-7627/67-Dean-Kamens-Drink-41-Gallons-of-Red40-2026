package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field for values.
 */
abstract class ValueDashboardField<Inner> extends BaseDashboardField<Inner> {
    /**
     * A pushing or pulling dashboard field for values.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param fieldMode     whether the field is pushing or pulling.
     */
    protected ValueDashboardField(String subsystemName, String fieldName, Inner initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode.isPull() ? initialValue : null, fieldMode);
    }
}
