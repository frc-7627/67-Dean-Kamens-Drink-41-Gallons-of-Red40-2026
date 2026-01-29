package frc.robot.resources.gameinfo;

public interface SpecificGameInfoSupplier {
    public static enum Phase {
        AUTO, TRANSITION, TELEOP_1, TELEOP_2, TELEOP_3, TELEOP_4, ENDGAME;
    }

    Phase getPhase();

    boolean isHubActive();
}
