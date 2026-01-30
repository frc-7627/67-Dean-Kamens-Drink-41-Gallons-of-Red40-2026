package frc.robot.subsystems.controlstate;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface GlobalControlState extends ControlStateSupplier, Subsystem {
    void setControlState(ControlState controlState);

    static GlobalControlState create() {
        return new GlobalControlStateImpl();
    }
}
