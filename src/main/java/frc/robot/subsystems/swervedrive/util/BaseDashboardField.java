package frc.robot.subsystems.swervedrive.util;

/**
 * A dashboard field.
 */
public abstract class BaseDashboardField<Subsystem, Inner> {
    private final String pushKey;
    private final String pullKey;
    private Inner inner;
    private final Inner defaultValue;
    private final ValueMode valueMode;

    /**
     * A dashboard field that is only pushed.
     * 
     * @param subsystem the subsystem.
     * @param fieldName the name of the field.
     * @param inner     the field's value.
     */
    protected BaseDashboardField(Subsystem subsystem, String fieldName, Inner inner) {
        final String subsystemName = subsystem.getClass().getSimpleName();
        final ValueMode valueMode = ValueMode.PUSH_ONLY;
        this.pushKey = getPushKey(subsystemName, fieldName);
        this.pullKey = getPullKey(subsystemName, fieldName, valueMode);
        this.inner = inner;
        this.defaultValue = null;
        this.valueMode = valueMode;
    }

    /**
     * A dashboard field that is pushed and pulled.
     * 
     * @param subsystem    the subsystem.
     * @param fieldName    the name of the field.
     * @param inner        the field's value.
     * @param defaultValue the field's default value.
     * @param isConstant   whether the field will be treated as a constant.
     */
    protected BaseDashboardField(Subsystem subsystem, String fieldName, Inner inner, Inner defaultValue,
            boolean isConstant) {
        final String subsystemName = subsystem.getClass().getSimpleName();
        final ValueMode valueMode = isConstant ? ValueMode.PUSH_AND_PULL_CONST : ValueMode.PUSH_AND_PULL;
        this.pushKey = getPushKey(subsystemName, fieldName);
        this.pullKey = getPullKey(subsystemName, fieldName, valueMode);
        this.inner = inner;
        this.defaultValue = defaultValue;
        this.valueMode = valueMode;
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
     * Get the pull-const key from the subsystem name and the field name.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the field name.
     * @return the pull-const key.
     */
    private static String getPullConstKey(String subsystemName, String fieldName) {
        return String.format("{0}/const/{1}", subsystemName, fieldName);
    }

    private static String getPullKey(String subsystemName, String fieldName, ValueMode valueMode) {
        return switch (valueMode) {
            case PUSH_ONLY -> null;
            case PUSH_AND_PULL_CONST -> getPullConstKey(subsystemName, fieldName);
            case PUSH_AND_PULL -> getPushKey(subsystemName, fieldName);
            default -> throw new IllegalArgumentException();
        };
    }

    public Inner getInner() {
        return inner;
    }

    public void setInner(Inner inner) {
        this.inner = inner;
    }

    abstract protected void send(String pushKey);

    abstract protected void recv(String pullKey, Inner defaultValue);

    public void push() {
        send(pushKey);
    }

    public void pull() {
        if (valueMode.isPull()) {
            recv(pullKey, defaultValue);
        }
    }
}
