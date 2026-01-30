package frc.robot.resources.dashboard;

public interface PushingField<Pulled, Pushed>
        extends DashboardField, Pulling<Pulled>, Pushing<Pushed> {
}
