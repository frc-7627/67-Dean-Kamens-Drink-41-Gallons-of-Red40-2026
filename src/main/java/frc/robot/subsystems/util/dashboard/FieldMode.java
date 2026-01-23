package frc.robot.subsystems.util.dashboard;

public enum FieldMode {
    PUSH, PULL;

    public boolean isPush() {
        return this.equals(PUSH);
    }

    public boolean isPull() {
        return this.equals(PULL);
    }
}
