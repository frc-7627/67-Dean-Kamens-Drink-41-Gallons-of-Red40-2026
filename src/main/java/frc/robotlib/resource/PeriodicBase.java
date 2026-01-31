package frc.robotlib.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class PeriodicBase {
    private final List<Subresource> subresources = new ArrayList<>();

    protected PeriodicBase() {}

    protected PeriodicBase(Collection<? extends Subresource> subresources) {
        this.subresources.addAll(subresources);
    }

    protected void periodic() {
        subresources.forEach(Subresource::periodic);
    }

    protected void addSubresource(Subresource subresource) {
        subresources.add(subresource);
    }

    protected void addAllSubresources(Collection<? extends Subresource> allSubresources) {
        subresources.addAll(allSubresources);
    }
}
