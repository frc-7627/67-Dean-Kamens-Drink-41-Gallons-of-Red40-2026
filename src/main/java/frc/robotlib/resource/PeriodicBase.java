package frc.robotlib.resource;

import java.util.Collection;
import java.util.List;

class PeriodicBase {
    private final List<Subresource> subresources = List.of();

    protected PeriodicBase() {}

    protected PeriodicBase(Collection<Subresource> subresources) {
        this.subresources.addAll(subresources);
    }

    protected void periodic() {
        subresources.forEach(Subresource::periodic);
    }
}
