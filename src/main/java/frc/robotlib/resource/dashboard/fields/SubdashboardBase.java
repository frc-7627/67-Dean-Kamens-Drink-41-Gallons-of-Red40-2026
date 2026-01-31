package frc.robotlib.resource.dashboard.fields;

import java.util.Collection;
import java.util.Optional;
import frc.robotlib.resource.SubresourceBase;
import frc.robotlib.resource.dashboard.Subdashboard;

public abstract class SubdashboardBase extends SubresourceBase implements Subdashboard {
    private final String superdashboardName;
    private final Optional<String> subdashboardName;

    protected SubdashboardBase(Collection<? extends Subdashboard> subdashboards,
            String superdashboardName) {
        super(subdashboards);
        this.superdashboardName = superdashboardName;
        this.subdashboardName = Optional.empty();
    }

    protected SubdashboardBase(Collection<? extends Subdashboard> subdashboards,
            String superdashboardName, String subdashboardName) {
        super(subdashboards);
        this.superdashboardName = superdashboardName;
        this.subdashboardName = Optional.of(subdashboardName);
    }

    protected SubdashboardBase(String superdashboardName, String subdashboardName) {
        this.superdashboardName = superdashboardName;
        this.subdashboardName = Optional.of(subdashboardName);
    }

    protected SubdashboardBase(String superdashboardName) {
        this.superdashboardName = superdashboardName;
        this.subdashboardName = Optional.empty();
    }

    @Override
    public final Optional<String> getSuperdashboardName() {
        return Optional.of(superdashboardName);
    }

    @Override
    public final Optional<String> getDashboardName() {
        return subdashboardName;
    }
}
