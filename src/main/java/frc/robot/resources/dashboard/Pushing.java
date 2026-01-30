package frc.robot.resources.dashboard;

interface Pushing<Pushed> {
    boolean checkPushed(Pushed pushed);

    void setPushed(Pushed pushed);
}
