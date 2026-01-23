package frc.robot.subsystems.util.dashboard;

/**
 * Whether a field is pushing or pulling.
 */
public enum FieldMode {
    /**
     * A pushing field.
     */
    PUSH, 
    /**
     * A pulling field.
     */
    PULL;

    /**
     * @return whether the field is pushing.
     */
    public final boolean isPush() {
        return this.equals(PUSH);
    }

    /**
     * @return whether the field is pulling.
     */
    public final boolean isPull() {
        return this.equals(PULL);
    }
}
