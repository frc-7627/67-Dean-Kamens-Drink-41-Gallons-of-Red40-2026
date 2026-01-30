package frc.robotlib.resource;

import java.util.Collection;
import java.util.List;

final class ResourceSchedulerImpl implements ResourceScheduler {
    private static final ResourceSchedulerImpl INSTANCE = new ResourceSchedulerImpl();

    private final List<Resource> resources = List.of();

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
        resources.forEach(Resource::periodic);
    }

    static ResourceSchedulerImpl getInstance() {
        return INSTANCE;
    }
}
