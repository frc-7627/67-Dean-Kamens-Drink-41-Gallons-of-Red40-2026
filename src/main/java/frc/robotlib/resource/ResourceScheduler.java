package frc.robotlib.resource;

import java.util.Collection;

public interface ResourceScheduler {
    void schedule(Resource resource);

    default void scheduleAll(Collection<Resource> resources) {
        resources.forEach(this::schedule);
    }

    void run();

    static ResourceScheduler getInstance() {
        return ResourceSchedulerImpl.getInstance();
    }
}
