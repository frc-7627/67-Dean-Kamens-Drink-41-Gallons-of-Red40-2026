package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.Controllable;

/**
 * Commands that continuously control a target subsystem using a control strategy.
 * 
 * @param <Control> control strategies
 * @param <TargetSubsystem> target subsystems
 */
public final class ControlCommand<
    Control, 
    TargetSubsystem extends Controllable<Control> & Subsystem
> extends FunctionalCommand {
    /**
     * Create a command that continuously controls the target subsystem using the provided control
     * strategy.
     * 
     * @param targetSubsystem the target subsystem
     * @param control the provided control strategy
     */
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
