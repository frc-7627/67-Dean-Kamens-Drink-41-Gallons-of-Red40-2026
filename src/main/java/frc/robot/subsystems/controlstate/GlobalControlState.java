package frc.robot.subsystems.controlstate;

import java.util.function.Consumer;

/**
 * Interface managing the current global control mode of the robot.
 */
public interface GlobalControlState extends ControlStateToggler {
    public static enum ControlState {
        NORMAL,
        MANUAL;

        public boolean isManual() {
            return equals(MANUAL);
        }
    }

    void trigger();

    void onNewControlState(Consumer<ControlState> action);

    static GlobalControlState create() {
        return new GlobalControlStateImpl();
    }
}
