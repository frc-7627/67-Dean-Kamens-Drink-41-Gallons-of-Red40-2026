package frc.bofalib.subsystem;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class SharedSubsystemBase extends SubsystemBase implements SharedSubsystem {
    public SharedSubsystemBase() {
        CommandScheduler.getInstance().unregisterSubsystem(this);
    }

    public SharedSubsystemBase(String name) {
        super(name);
        
        CommandScheduler.getInstance().unregisterSubsystem(this);
    }
}
