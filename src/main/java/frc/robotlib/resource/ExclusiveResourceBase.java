package frc.robotlib.resource;

import java.util.Collection;

public class ExclusiveResourceBase extends PeriodicBase implements ExclusiveResource {
    protected ExclusiveResourceBase() {}

    protected ExclusiveResourceBase(Collection<? extends Subresource> subresources) {
        super(subresources);
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
