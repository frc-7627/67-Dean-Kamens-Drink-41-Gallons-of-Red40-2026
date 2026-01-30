package frc.robot.resources.dashboard;

public interface PushingDashboardField<Pulled, Pushed>
        extends DashboardField, Pulling<Pulled>, Pushing<Pushed> {
}
