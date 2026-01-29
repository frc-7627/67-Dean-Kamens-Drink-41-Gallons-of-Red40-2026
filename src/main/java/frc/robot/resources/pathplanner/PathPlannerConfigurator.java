package frc.robot.resources.pathplanner;

public interface PathPlannerConfigurator {
    PathPlannerConfigurator configure() throws PathPlannerConfigException;

    void init();

    // TODO: factory method
}
