package frc.robot.resources.gameinfo;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 * Interface to supply information about the game, specifically the current
 * alliance of the team.
 */
public interface GeneralGameInfoSupplier {
    Alliance getAlliance();

    void onAllianceSet(Runnable action);
}
