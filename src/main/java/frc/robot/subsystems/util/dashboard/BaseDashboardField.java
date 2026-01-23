package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field.
 */
abstract class BaseDashboardField<Inner> implements DashboardField {
    /**
     * The key for the dashboard field.
     * 
     * Should be of the form {@code "Subsystem Name/Field Name"}
     */
    private final String key;
    /**
     * The inner value of the dashboard field.
     */
    private Inner innerValue;
    /**
     * Whether the field is pushing or pulling.
     */
    private final FieldMode fieldMode;

    /**
     * A pushing or pulling dashboard field.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param fieldMode     whether the field is pushing or pulling.
     */
    protected BaseDashboardField(String subsystemName, String fieldName, Inner initialValue, FieldMode fieldMode) {
        this.key = getKey(subsystemName, fieldName);
        this.innerValue = initialValue;
        this.fieldMode = fieldMode;
    }

    /**
     * Get the key from the subsystem name and field name.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the field name.
     * @return the key.
     */
    private static String getKey(String subsystemName, String fieldName) {
        return String.format("%s/%s", subsystemName, fieldName);
    }

    /**
     * @return the inner value.
     */
    public final Inner getInnerValue() {
        return innerValue;
    }

    /**
     * Set the inner value.
     * 
     * @param innerValue the inner value.
     */
    public final void setInnerValue(Inner innerValue) {
        this.innerValue = innerValue;
    }

    /**
     * @return whether the field is pushing or pulling.
     */
    protected final FieldMode getFieldMode() {
        return fieldMode;
    }

    /**
     * Push the value to the dashboard using the provided key.
     * 
     * @param key the provided key.
     */
    abstract protected void push(String key);

    /**
     * Pull the value from the dashboard using the provided key.
     * 
     * @param key the provided key.
     */
    abstract protected void pull(String key);

    /**
     * Gets the default value of the field. Should fail if the field is not pulling.
     * 
     * @return the default value.
     */
    abstract protected Inner getDefaultValue();

    /**
     * {@inheritDoc}
     * 
     * If the field is pulling, push the current value.
     * 
     * @see #push(String)
     */
    @Override
    public final void init() {
        if (fieldMode.isPull()) {
            push(key);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * If the field is pushing, push the current value.
     * If the field is pulling, pull the current value.
     * 
     * @see #push(String)
     * @see #pull(String)
     */
    @Override
    public final void update() {
        switch (fieldMode) {
            case PUSH -> {
                push(key);
            }
            case PULL -> {
                pull(key);
            }
        }
    }
}
