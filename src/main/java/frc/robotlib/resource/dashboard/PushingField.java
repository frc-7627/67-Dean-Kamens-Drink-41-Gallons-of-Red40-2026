package frc.robotlib.resource.dashboard;

public interface PushingField<Pulled, Pushed>
        extends DashboardField, Pulling<Pulled>, Pushing<Pushed> {
}
