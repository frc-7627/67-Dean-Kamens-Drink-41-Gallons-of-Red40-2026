package frc.robot.subsystems.util.dashboard;

/**
 * Whether a field is pushing or pulling.
 */
public enum FieldMode {
    /**
     * A pushing field.
     * 
     * @see #isPush()
     */
    PUSH, 
    /**
     * A pulling field.
     * 
     * @see #isPull()
     */
    PULL;

    /**
     * @return whether the field is pushing.
     * @see #PUSH
     */
    public final boolean isPush() {
        return this.equals(PUSH);
    }

    /**
     * @return whether the field is pulling.
     * @see #PULL
     */
    public final boolean isPull() {
        return this.equals(PULL);
    }
}
