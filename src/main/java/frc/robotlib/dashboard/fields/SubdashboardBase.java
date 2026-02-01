package frc.robotlib.dashboard.fields;

import java.util.Optional;
import frc.robotlib.dashboard.Subdashboard;
import frc.robotlib.resource.SubresourceBase;

public abstract class SubdashboardBase extends SubresourceBase implements Subdashboard {
    private final String superdashboardName;
    private final Optional<String> subdashboardName;

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
