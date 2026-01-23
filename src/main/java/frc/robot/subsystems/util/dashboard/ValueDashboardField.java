package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field for values.
 * 
 * @param <Inner> an inner value.
 * @see DashboardField
 * @see BaseDashboardField
 * @see DashboardBoolean
 * @see DashboardDouble
 * @see #ValueDashboardField(String, String, Inner, FieldMode)
 */
abstract class ValueDashboardField<Inner> extends BaseDashboardField<Inner> {
    /**
     * A pushing or pulling dashboard field for values.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param fieldMode     whether the field is pushing or pulling.
     * @see FieldMode
     * @see FieldMode#isPull()
     * @see BaseDashboardField#BaseDashboardField(String, String, Inner, Inner, FieldMode)
     */
    protected ValueDashboardField(String subsystemName, String fieldName, Inner initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode.isPull() ? initialValue : null, fieldMode);
    }
}
