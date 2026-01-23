package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
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

    private final EventLoop eventLoop = new EventLoop();
    private final BooleanEvent allianceSetEvent;
    private Phase phase = Constants.GameInfoConstants.START_PHASE;
    private Alliance alliance = Constants.GameInfoConstants.DEFAULT_ALLIANCE;
    private boolean isDistinctAlliance = false;
    private boolean hasGotAlliance = false;

    /**
     * Subsystem for getting game info.
     */
    public GameInfo() {
        BooleanEvent isDistinctAllianceEvent = new BooleanEvent(eventLoop, () -> isDistinctAlliance);
        BooleanEvent hasGotAllianceEvent = new BooleanEvent(eventLoop, () -> hasGotAlliance);
        this.allianceSetEvent = isDistinctAllianceEvent
                .rising()
                .or(hasGotAllianceEvent.rising());
    }

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
        Optional<Alliance> newAllianceOption = DriverStation.getAlliance();

        if (newAllianceOption.isPresent()) {
            Alliance newAlliance = newAllianceOption.get();

            isDistinctAlliance = newAlliance != alliance;
            hasGotAlliance = true;

            alliance = newAlliance;
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
        } else {
            isDistinctAlliance = false;
        }

        eventLoop.poll();
    }

    /**
     * Bind the action for when an alliance has been set by the driver station.
     * 
     * @param action the action.
     */
    public void onAllianceSet(Runnable action) {
        allianceSetEvent.ifHigh(action);
    }
}
