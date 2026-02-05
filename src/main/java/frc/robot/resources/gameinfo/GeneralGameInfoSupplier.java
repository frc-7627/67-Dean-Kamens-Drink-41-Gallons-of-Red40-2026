package frc.robot.resources.gameinfo;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.bofalib.resource.SharedResource;

/**
 * Interface to supply information about the game, specifically the current
 * alliance of the team.
 */
public interface GeneralGameInfoSupplier extends SharedResource {
    /**
     * @return the current alliance.
     */
    Alliance getAlliance();
}
