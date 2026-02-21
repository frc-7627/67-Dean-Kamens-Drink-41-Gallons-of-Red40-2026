package frc.robot.subsystems.shared.gameinfo;

import java.util.function.Consumer;
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

    /**
     * Bind the action for when an alliance has been set by the driver station.
     * 
     * @param action the action.
     */
    void onAllianceSet(Consumer<Alliance> action);
}
