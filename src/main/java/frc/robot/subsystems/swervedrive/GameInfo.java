package frc.robot.subsystems.swervedrive;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Optional;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import frc.robot.subsystems.swervedrive.util.Phase;
import frc.robot.Constants;

public class GameInfo extends SubsystemBase {
    private Phase phase;
    private Alliance alliance;

    /**
     * Subsystem for getting game info.
     */
    public GameInfo() {
        this.phase = Constants.GameInfoConstants.START_PHASE;
        this.alliance = Constants.GameInfoConstants.DEFAULT_ALLIANCE;
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

    public int getPhaseNum() {
        return phase.toInt();
    }

    public int getAllianceNum() {
        switch (alliance) {
            case Red -> {
                return 0;
            }
            case Blue -> {
                return 1;
            }
            default -> {
                // Never happens.
                throw new IllegalStateException("Alliance should be Red or Blue!");
            }
        }
    }

    /**
     * Update the alliance to match the driver station.
     */
    void updateAlliance() {
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
