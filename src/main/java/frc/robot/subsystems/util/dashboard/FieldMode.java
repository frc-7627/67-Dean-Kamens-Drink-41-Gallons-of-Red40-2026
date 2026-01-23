package frc.robot.subsystems.util.dashboard;

public enum FieldMode {
    PUSH, PULL;

    public final boolean isPush() {
        return this.equals(PUSH);
    }

    public final boolean isPull() {
        return this.equals(PULL);
    }
}
