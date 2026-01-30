package frc.robotlib.resource;

import java.util.Collection;

public class SharedSubresourceBase extends PeriodicBase implements SharedSubresource {
    protected SharedSubresourceBase() {}

    protected SharedSubresourceBase(Collection<? extends SharedSubresource> subresources) {
        super(subresources);
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
