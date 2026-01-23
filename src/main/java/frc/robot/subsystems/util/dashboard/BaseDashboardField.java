package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field.
 * 
 * @see #BaseDashboardField(String, String, Object, Object, FieldMode)
 */
abstract class BaseDashboardField<Inner> implements DashboardField {
    /**
     * The key for the dashboard field.
     * 
     * Should be of the form {@code "Subsystem Name/Field Name"}
     * 
     * @see #getKey(String, String)
     */
    private final String key;
    /**
     * The inner value of the dashboard field.
     * 
     * @see #getInnerValue()
     * @see #setInnerValue(Object)
     */
    private Inner innerValue;
    /**
     * The default value of the dashboard field.
     * 
     * @see #getDefaultValue()
     */
    private final Inner defaultValue;
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
     * @param defaultValue  the field's default value.
     * @param fieldMode     whether the field is pushing or pulling.
     * @see #key
     * @see #innerValue
     * @see #defaultValue
     * @see #fieldMode
     * @see #getKey(String, String)
     */
    protected BaseDashboardField(String subsystemName, String fieldName, Inner initialValue, Inner defaultValue,
            FieldMode fieldMode) {
        this.key = getKey(subsystemName, fieldName);
        this.innerValue = initialValue;
        this.defaultValue = defaultValue;
        this.fieldMode = fieldMode;
    }

    /**
     * Get the key from the subsystem name and field name.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the field name.
     * @return the key.
     * @see String#format(String, String, String)
     */
    private static String getKey(String subsystemName, String fieldName) {
        return String.format("%s/%s", subsystemName, fieldName);
    }

    /**
     * @return the inner value.
     * @see #innerValue
     */
    public final Inner getInnerValue() {
        return innerValue;
    }

    /**
     * Set the inner value.
     * 
     * @param innerValue the inner value.
     * @see #innerValue
     */
    public final void setInnerValue(Inner innerValue) {
        this.innerValue = innerValue;
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
     * Gets the default value of the field. Fails if the field is not pulling.
     * 
     * @return the default value.
     * @throws IllegalStateException
     * @see #fieldMode
     * @see #defaultValue
     * @see FieldMode#isPull()
     */
    protected final Inner getDefaultValue() {
        if (!fieldMode.isPull()) {
            throw new IllegalStateException("Default value only exists for pulling fields!");
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     * 
     * If the field is pulling, push the current value.
     * 
     * @see #fieldMode
     * @see #key
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
     * @see #fieldMode
     * @see #key
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
