package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field.
 * 
 * @param <Inner> a value of the field.
 * @see DashboardObject
 * @see DashboardValue
 * @see #BaseDashboardField(String, String, Inner, Inner, FieldMode)
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
     * The current inner value of the dashboard field.
     * 
     * @see #getInnerValue()
     * @see #setInnerValue(Inner)
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
     * 
     * @see FieldMode
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
     * @see FieldMode
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

        if (!isValid()) {
            throw new IllegalStateException("Initial dashboard state must be valid!");
        }
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
     * @return the current inner value.
     * @see #innerValue
     */
    public final Inner getInnerValue() {
        return innerValue;
    }

    /**
     * Set the current inner value to the new inner value. If the field is pulling,
     * also push the new inner value with the key.
     * 
     * @param innerValue the new inner value.
     * @see #innerValue
     * @see #pushIfPull()
     */
    public final void setInnerValue(Inner innerValue) {
        this.innerValue = innerValue;

        if (!isValid()) {
            this.innerValue = defaultValue;
        }

        pushIfPull();
    }

    /**
     * Push the current value to the dashboard using the provided key.
     * 
     * @param key the provided key.
     */
    abstract protected void pushWithKey(String key);

    /**
     * Pull the current value from the dashboard using the provided key.
     * 
     * @param key the provided key.
     */
    abstract protected void pullWithKey(String key);

    /**
     * Push the field.
     * 
     * Push the current value with the key.
     * 
     * @see #key
     * @see #pushWithKey(String)
     */
    private void push() {
        pushWithKey(key);
    }

    /**
     * Pull the field.
     * 
     * Pull the current value with the key.
     * 
     * @see #key
     * @see #pullWithKey(String)
     */
    private void pull() {
        pullWithKey(key);
    }

    /**
     * Push the field if the field is pulling.
     * 
     * @see #fieldMode
     * @see #push()
     * @see FieldMode#isPull()
     */
    private void pushIfPull() {
        if (fieldMode.isPull()) {
            push();
        }
    }

    /**
     * @param value the value
     * @return whether the value is valid.
     */
    protected boolean checkValue(Inner value) {
        return true;
    }

    /**
     * @return whether the field is in a valid state.
     * @see #innerValue
     * @see #defaultValue
     * @see #checkValue(Inner)
     */
    private boolean isValid() {
        return checkValue(innerValue) && checkValue(defaultValue);
    }

    /**
     * Gets the default value of the field. Fails if the field is not pulling.
     * 
     * @return the default value.
     * @throws IllegalStateException if the field is not pulling.
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
     * If the field is pulling, push.
     * 
     * @see #fieldMode
     * @see #key
     * @see #pushWithKey(String)
     * @see FieldMode#isPull()
     */
    @Override
    public final void init() {
        pushIfPull();
    }

    /**
     * {@inheritDoc}
     * 
     * If the field is pushing, push the field.
     * If the field is pulling, pull the field.
     * 
     * @see #fieldMode
     * @see #push()
     * @see #pull()
     */
    @Override
    public final void update() {
        switch (fieldMode) {
            case PUSH -> {
                push();
            }
            case PULL -> {
                pull();
            }
        }
    }
}
