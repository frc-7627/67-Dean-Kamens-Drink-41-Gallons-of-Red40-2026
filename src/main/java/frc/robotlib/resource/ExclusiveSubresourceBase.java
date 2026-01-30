package frc.robotlib.resource;

import java.util.Collection;

public class ExclusiveSubresourceBase extends SubresourceBase implements ExclusiveSubresource {
    protected ExclusiveSubresourceBase() {}

    protected ExclusiveSubresourceBase(Collection<? extends Subresource> subresources) {
        super(subresources);
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
