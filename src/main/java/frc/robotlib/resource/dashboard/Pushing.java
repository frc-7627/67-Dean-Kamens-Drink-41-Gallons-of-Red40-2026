package frc.robotlib.resource.dashboard;

interface Pushing<Pushed> {
    boolean checkPushed(Pushed pushed);

    void setPushed(Pushed pushed);
}
