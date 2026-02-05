package frc.bofalib.resource;

import java.util.Collection;

public class SharedSubresourceBase extends SubresourceBase implements SharedSubresource {
    protected SharedSubresourceBase() {}

    protected SharedSubresourceBase(Collection<? extends SharedSubresource> subresources) {
        super(subresources);
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
