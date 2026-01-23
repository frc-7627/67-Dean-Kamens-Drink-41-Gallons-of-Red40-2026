package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field.
 */
abstract class BaseDashboardField<Inner> implements DashboardField {
    private final String key;
    private Inner innerValue;
    private final FieldMode fieldMode;

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

    protected final FieldMode getFieldMode() {
        return fieldMode;
    }

    /**
     * Push the value to the dashboard using the provided key.
     * 
     * @param key the provided key.
     */
    abstract protected void send(String key);

    /**
     * Pull the value from the dashboard using the provided key.
     * 
     * @param key the provided key.
     */
    abstract protected void recv(String key);

    /**
     * @return the default value.
     */
    abstract protected Inner getDefaultValue();

    /**
     * Initialize the field.
     * 
     * If the field is pulling, push.
     */
    public final void init() {
        if (fieldMode.isPull()) {
            send(key);
        }
    }

    /**
     * Update the field.
     * 
     * Push or pull the field to or from the dashboard.
     */
    public final void update() {
        switch (fieldMode) {
            case PUSH -> {
                send(key);
            }
            case PULL -> {
                recv(key);
            }
        }
    }
}
