package frc.bofalib.dashboard;

interface Pulling<Pulled> {
    boolean checkPulled(Pulled pulled);

    Pulled getPulled();
}
