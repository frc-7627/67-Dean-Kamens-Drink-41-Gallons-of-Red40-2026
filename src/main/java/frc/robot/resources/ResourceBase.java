package frc.robot.resources;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

public abstract class ResourceBase implements Resource {
    protected ResourceBase() {
        CommandScheduler.getInstance().schedule(new RunCommand(this::periodic));
    }
}
