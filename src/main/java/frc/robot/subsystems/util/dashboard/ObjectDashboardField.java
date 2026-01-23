package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field for objects.
 */
abstract class ObjectDashboardField<Inner> extends BaseDashboardField<Inner> {
    /**
     * A pushing dashboard field for objects.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    protected ObjectDashboardField(String subsystemName, String fieldName, Inner initialValue) {
        super(subsystemName, fieldName, initialValue, null, FieldMode.PUSH);
    }

    /**
     * A pulling dashboard field for objects.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param defaultValue  the field's default value.
     */
    protected ObjectDashboardField(String subsystemName, String fieldName, Inner initialValue, Inner defaultValue) {
        super(subsystemName, fieldName, initialValue, defaultValue, FieldMode.PULL);
    }
}
