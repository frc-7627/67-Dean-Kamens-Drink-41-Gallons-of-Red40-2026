package frc.robot.resources;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * A shared resource that may have periodic behavior.
 */
public abstract class Resource {
    protected Resource() {
        CommandScheduler.getInstance().schedule(new RunCommand(this::periodic));
    }

    public void periodic() {}
}
