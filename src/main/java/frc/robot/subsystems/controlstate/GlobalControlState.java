package frc.robot.subsystems.controlstate;

import java.util.function.Consumer;

public interface GlobalControlState extends ControlStateToggler {
    void onNewControlState(Consumer<ControlState> action);

    static GlobalControlState create() {
        return new GlobalControlStateImpl();
    }
}
