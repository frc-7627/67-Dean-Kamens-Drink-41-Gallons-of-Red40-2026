package frc.robotlib.resource.dashboard;

interface Pulling<Pulled> {
    boolean checkPulled(Pulled pulled);

    Pulled getPulled();
}
