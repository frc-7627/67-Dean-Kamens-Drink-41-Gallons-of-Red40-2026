package frc.robotlib.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Watchdog;

final class ResourceSchedulerImpl implements ResourceScheduler {
    private static final ResourceSchedulerImpl INSTANCE = new ResourceSchedulerImpl();

    private final List<Resource> resources = new ArrayList<>();

    private final Watchdog watchdog = new Watchdog(TimedRobot.kDefaultPeriod, () -> {});

    private ResourceSchedulerImpl() {}

    @Override
    public void schedule(Resource resource) {
        resources.add(resource);
    }

    @Override
    public void scheduleAll(Collection<Resource> allResources) {
        resources.addAll(allResources);
    }

    @Override
    public void run() {
        watchdog.reset();

        resources.forEach(resource -> {
            resource.periodic();
            watchdog.addEpoch(resource.getName() + ".periodic()");
        });

        watchdog.disable();

        if (watchdog.isExpired()) {
            System.out.println("ResourceScheduler loop overrun");
            watchdog.printEpochs();
        }
    }

    static ResourceSchedulerImpl getInstance() {
        return INSTANCE;
    }
}
