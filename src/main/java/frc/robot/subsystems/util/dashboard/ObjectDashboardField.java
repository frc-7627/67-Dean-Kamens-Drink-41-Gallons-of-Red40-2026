package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field for objects.
 */
abstract class ObjectDashboardField<Inner> extends BaseDashboardField<Inner> {
    private final Inner defaultValue;

    /**
     * A pushing dashboard field for objects.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    protected ObjectDashboardField(String subsystemName, String fieldName, Inner initialValue) {
        super(subsystemName, fieldName, initialValue, FieldMode.PUSH);

        this.defaultValue = null;
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
        super(subsystemName, fieldName, initialValue, FieldMode.PULL);

        this.defaultValue = defaultValue;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException
     */
    @Override
    protected final Inner getDefaultValue() {
        if (!getFieldMode().isPull()) {
            throw new IllegalStateException("Default value only exists for pulling fields!");
        }
        return defaultValue;
    }
}
