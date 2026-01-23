package frc.robot.subsystems.dashboard;

/**
 * A dashboard field.
 */
abstract class BaseDashboardField<Inner> {
    private final String pushKey;
    private final String pullKey;
    private Inner innerValue;
    private final Inner defaultValue;
    private final boolean isPull;

    /**
     * A dashboard field that is pushed and possibly pulled.
     * 
     * @param isPull        whether the field is pulled.
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    protected BaseDashboardField(boolean isPull, String subsystemName, String fieldName, Inner initialValue) {
        this.pushKey = getPushKey(subsystemName, fieldName);
        this.pullKey = isPull ? getPullKey(subsystemName, fieldName) : null;
        this.innerValue = initialValue;
        this.defaultValue = isPull ? initialValue : null;
        this.isPull = isPull;
    }

    /**
     * A dashboard field that is only pushed.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    protected BaseDashboardField(String subsystemName, String fieldName, Inner initialValue) {
        this.pushKey = getPushKey(subsystemName, fieldName);
        this.pullKey = null;
        this.innerValue = initialValue;
        this.defaultValue = null;
        this.isPull = false;
    }

    /**
     * A dashboard field that is pushed and pulled.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param defaultValue  the field's default value.
     */
    protected BaseDashboardField(String subsystemName, String fieldName, Inner initialValue, Inner defaultValue) {
        this.pushKey = getPushKey(subsystemName, fieldName);
        this.pullKey = getPullKey(subsystemName, fieldName);
        this.innerValue = initialValue;
        this.defaultValue = defaultValue;
        this.isPull = true;
    }

    /**
     * Get the push key from the subsystem name and field name.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the field name.
     * @return the push key.
     */
    private static String getPushKey(String subsystemName, String fieldName) {
        return String.format("{0}/{1}", subsystemName, fieldName);
    }

    /**
     * Get the pull key from the subsystem name and the field name.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the field name.
     * @return the pull key.
     */
    private static String getPullKey(String subsystemName, String fieldName) {
        return String.format("{0}/const/{1}", subsystemName, fieldName);
    }

    /**
     * @return the inner value.
     */
    public Inner getInnerValue() {
        return innerValue;
    }

    /**
     * Set the inner value.
     * 
     * @param innerValue the inner value.
     */
    public void setInnerValue(Inner innerValue) {
        this.innerValue = innerValue;
    }

    /**
     * Send the value to the dashboard using the push key.
     * 
     * @param pushKey the push key.
     */
    abstract protected void send(String pushKey);

    /**
     * Receive the value from the dashboard using the pull key, falling back on the
     * provided default.
     * 
     * @param pullKey      the pull key.
     * @param defaultValue the provided default.
     */
    abstract protected void recv(String pullKey, Inner defaultValue);

    /**
     * Push the field to the dashboard.
     */
    protected void push() {
        send(pushKey);
    }

    /**
     * Pull the field from the dashboard.
     */
    protected void pull() {
        if (isPull) {
            recv(pullKey, defaultValue);
        }
    }

    /**
     * Update the field.
     * 
     * Push and pull the field to and from the dashboard.
     */
    public void update() {
        push();
        pull();
    }
}
