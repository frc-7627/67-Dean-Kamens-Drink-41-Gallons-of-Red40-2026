package frc.bofalib.subsystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public final class CommandSchedulerWrapper {
    private static final CommandSchedulerWrapper INSTANCE = new CommandSchedulerWrapper();

    public static CommandSchedulerWrapper getInstance() {
        return INSTANCE;
    }

    private final CommandScheduler commandScheduler = CommandScheduler.getInstance();

    private final Set<SharedSubsystem> sharedSubsystems = new LinkedHashSet<>();

    private final Collection<Runnable> periodicActions = new ArrayList<>();
    
    private CommandSchedulerWrapper() {}

    public void run() {
        commandScheduler.run();;

        sharedSubsystems.forEach(SharedSubsystem::periodic);

        periodicActions.forEach(Runnable::run);
    }

    public void registerSharedSubsystem(SharedSubsystem subsystem) {
        if (subsystem == null) {
            DriverStation.reportWarning("Tried to register a null subsystem", true);
            return;
        }

        if (sharedSubsystems.contains(subsystem)) {
            DriverStation.reportWarning("Tried to register an already-registered subsystem", true);
            return;
        }

        sharedSubsystems.add(subsystem);
    }

    public void registerPeriodicAction(Runnable periodicAction) {
        this.periodicActions.add(periodicAction);
    }

    public void registerPeriodicActions(Collection<? extends Runnable> periodicActions) {
        this.periodicActions.addAll(periodicActions);
    }
}
