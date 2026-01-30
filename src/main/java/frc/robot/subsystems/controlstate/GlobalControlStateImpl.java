package frc.robot.subsystems.controlstate;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

class GlobalControlStateImpl extends SubsystemBase implements GlobalControlState {
    private ControlState controlState = ControlState.NORMAL;

    @Override
    public ControlState getControlState() {
        return controlState;
    }

    @Override
    public void setControlState(ControlState controlState) {
        this.controlState = controlState;
    }
}
