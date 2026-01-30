package frc.robotlib.resource;

import java.util.Collection;

public class SharedResourceBase extends PeriodicBase implements SharedResource {
    protected SharedResourceBase() {}

    protected SharedResourceBase(Collection<Subresource> subresources) {
        super(subresources);
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
