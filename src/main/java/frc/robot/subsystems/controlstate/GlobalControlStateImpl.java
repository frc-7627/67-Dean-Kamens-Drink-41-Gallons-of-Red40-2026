package frc.robot.subsystems.controlstate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

class GlobalControlStateImpl extends SubsystemBase implements GlobalControlState {
    private final List<Consumer<ControlState>> listeners = new ArrayList<>();

    private ControlState controlState = ControlState.NORMAL;

    @Override
    public void trigger() {
        listeners.forEach(listener -> listener.accept(controlState));
    }

    @Override
    public void toggleControlState() {
        switch (controlState) {
            case NORMAL -> controlState = ControlState.MANUAL;
            case MANUAL -> controlState = ControlState.NORMAL;
        }

        trigger();
    }

    @Override
    public void onNewControlState(Consumer<ControlState> action) {
        listeners.add(action);
    }
}
