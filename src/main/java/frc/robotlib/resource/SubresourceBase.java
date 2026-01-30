package frc.robotlib.resource;

import java.util.Collection;

public class SubresourceBase extends PeriodicBase implements Subresource {
    protected SubresourceBase() {}

    protected SubresourceBase(Collection<? extends Subresource> subresources) {
        super(subresources);
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
