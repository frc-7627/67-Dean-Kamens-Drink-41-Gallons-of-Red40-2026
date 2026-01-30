package frc.robot.resources.dashboard;

interface Pulling<Pulled> {
    boolean checkPulled(Pulled pulled);

    Pulled getPulled();
}
