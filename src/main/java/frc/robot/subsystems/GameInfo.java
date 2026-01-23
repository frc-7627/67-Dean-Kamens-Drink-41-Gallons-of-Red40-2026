package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Optional;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import frc.robot.Constants;

public class GameInfo extends SubsystemBase {
    /**
     * A game phase.
     */
    public static enum Phase {
        AUTO, TRANSITION, TELEOP_1, TELEOP_2, TELEOP_3, TELEOP_4, ENDGAME;
    }

    private Phase phase = Constants.GameInfoConstants.START_PHASE;
    private Alliance alliance = Constants.GameInfoConstants.DEFAULT_ALLIANCE;

    /**
     * Subsystem for getting game info.
     */
    public GameInfo() {}

    /**
     * Gets the current phase.
     * 
     * @return The current phase.
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * Gets the current alliance.
     * 
     * @return The current alliance.
     */
    public Alliance getAlliance() {
        return alliance;
    }

    /**
     * Update the alliance to match the driver station.
     */
    private void updateAlliance() {
        Optional<Alliance> alliance_option = DriverStation.getAlliance();

        if (alliance_option.isPresent()) {
            alliance = alliance_option.get();
        }
    }

    /**
     * Run every cycle.
     * 
     * Updates the alliance, if disabled.
     */
    @Override
    public void periodic() {
        if (RobotState.isDisabled()) {
            updateAlliance();
        }
    }
}
