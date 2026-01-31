package frc.robot.subsystems.controlstate;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

class GlobalControlStateImpl extends SubsystemBase implements GlobalControlState {
    private ControlState controlState = ControlState.NORMAL;

    @Override
    public void toggleControlState() {}

    @Override
    public void onNewControlState(Consumer<ControlState> action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onNewControlState'");
    }
}
