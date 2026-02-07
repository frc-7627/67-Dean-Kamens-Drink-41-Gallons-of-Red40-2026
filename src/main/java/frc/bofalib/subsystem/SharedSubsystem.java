package frc.bofalib.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface SharedSubsystem extends Subsystem {
    default boolean isPeriodic() {
        return true;
    }
}
