package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import frc.bofalib.subsystem.ControllableSubsystem;

public final class ControlCommand<
    Control, 
    TargetSubsystem extends ControllableSubsystem<Control>
> extends FunctionalCommand {
    public ControlCommand(TargetSubsystem targetSubsystem, Control control) {
        super(
            () -> targetSubsystem.beginControl(control), 
            targetSubsystem::runControl, 
            interrupted -> targetSubsystem.endControl(),
            () -> false,
            targetSubsystem
        );
    }
}
