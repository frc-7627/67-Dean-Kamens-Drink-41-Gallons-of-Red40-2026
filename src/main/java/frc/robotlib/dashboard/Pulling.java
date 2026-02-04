package frc.robotlib.dashboard;

interface Pulling<Pulled> {
    boolean checkPulled(Pulled pulled);

    Pulled getPulled();
}
