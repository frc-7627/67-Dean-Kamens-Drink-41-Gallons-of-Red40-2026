package frc.robot.subsystems.controlstate;

public enum ControlState {
    NORMAL,
    MANUAL;

    public boolean isManual() {
        return equals(MANUAL);
    }
}
