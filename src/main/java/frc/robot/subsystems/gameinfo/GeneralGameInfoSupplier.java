package frc.robot.subsystems.gameinfo;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 * Interface to supply information about the game, specifically the current
 * alliance of the team.
 */
public interface GeneralGameInfoSupplier {
    /**
     * @return the current alliance.
     */
    Alliance getAlliance();
}
