package frc.robotlib.resource;

import java.util.Collection;

public class ResourceBase extends PeriodicBase implements Resource {
    protected ResourceBase() {}

    protected ResourceBase(Collection<? extends Subresource> subresources) {
        super(subresources);
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
