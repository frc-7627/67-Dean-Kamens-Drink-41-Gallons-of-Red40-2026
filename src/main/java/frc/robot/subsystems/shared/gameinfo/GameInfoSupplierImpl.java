package frc.robot.subsystems.shared.gameinfo;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.subsystem.SharedSubsystemBase;
import frc.bofalib.util.FunctionalUtil;
import frc.robot.Constants;

final class GameInfoSupplierImpl extends SharedSubsystemBase implements GameInfoSupplier {
    private static final Logger LOGGER = Logger.getLogger(GameInfoSupplier.class.getName());

    private static final String LOGGABLE_NAME = "Game Info";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private String gameData = DriverStation.getGameSpecificMessage();
    private final EventLoop eventLoop = new EventLoop();
    private final BooleanEvent allianceSetEvent;
    private Phase phase = Constants.GameInfoConstants.START_PHASE;
    private Alliance alliance;
    private boolean isDistinctAlliance = false;
    private final Timer teleopTimer = new Timer();

    /**
     * Resource for getting game info.
     */
    GameInfoSupplierImpl() {
        this.allianceSetEvent = new BooleanEvent(eventLoop, () -> isDistinctAlliance);

        this.alliance = DriverStation.getAlliance().orElse(
                Constants.GameInfoConstants.DEFAULT_ALLIANCE);

        allianceSetEvent.ifHigh(() -> {
            LOGGER.info("Alliance set from driver station.");
        });

        CommandSchedulerWrapper.getInstance().registerPeriodicAction(
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Time Left in Phase"),
                    true
                ), this::getTimeLeftInPhase,
                FunctionalUtil.hasChangedDoublePredicate()
            )
        );
    }

    @Override
    public Phase getPhase() {

        if (teleopTimer.hasElapsed(110)) {
            phase = Phase.ENDGAME;
        } else if (teleopTimer.hasElapsed(85)) {
            if (gameData.charAt(0) == 'R') {
                phase = Phase.TELEOP_ACTIVE;
            } else {
                phase = Phase.TELEOP_INACTIVE;
            }
        } else if (teleopTimer.hasElapsed(60)) {
            if (gameData.charAt(0) == 'B') {
                phase = Phase.TELEOP_ACTIVE;
            } else {
                phase = Phase.TELEOP_INACTIVE;
            }
        } else if (teleopTimer.hasElapsed(35)) {
            if (gameData.charAt(0) == 'R') {
                phase = Phase.TELEOP_ACTIVE;
            } else {
                phase = Phase.TELEOP_INACTIVE;
            }
        } else if (teleopTimer.hasElapsed(10)) {
            if (gameData.charAt(0) == 'B') {
                phase = Phase.TELEOP_ACTIVE;
            } else {
                phase = Phase.TELEOP_INACTIVE;
            }
        } else {
            phase = Phase.TRANSITION;
        }

        return phase;
    }

    private double getTimeOfPhaseBegin() {
        if (teleopTimer.hasElapsed(110)) {
            return 110;
        } else if (teleopTimer.hasElapsed(85)) {
            return 85;
        } else if (teleopTimer.hasElapsed(60)) {
            return 60;
        } else if (teleopTimer.hasElapsed(35)) {
            return 35;
        } else if (teleopTimer.hasElapsed(10)) {
            return 10;
        } else {
            return 0;
        }
    }

    private double getTimeOfPhaseEnd() {
        if (teleopTimer.hasElapsed(110)) {
            return 140;
        } else if (teleopTimer.hasElapsed(85)) {
            return 110;
        } else if (teleopTimer.hasElapsed(60)) {  //TODO: Figure out the times for these and those above
            return 85;
        } else if (teleopTimer.hasElapsed(35)) {
            return 60;
        } else if (teleopTimer.hasElapsed(10)) {
            return 35;
        } else {
            return 10;
        }
    }

    private double getTimeLeftInPhase() {
        if (getPhase().equals(Phase.AUTO)) {
            return DriverStation.getMatchTime();
        }
        return getTimeOfPhaseEnd() - teleopTimer.get();
    }

    @Override
    public void teleopInit() {
        teleopTimer.start();
    }

    @Override
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

            alliance = newAlliance;
        }
    }

    @Override
    public void periodic() {
        if (RobotState.isDisabled()) {
            updateAlliance();

            eventLoop.poll();
        } else {
            isDistinctAlliance = false;
        }
    }

    @Override
    public void onAllianceSet(Consumer<Alliance> action) {
        allianceSetEvent.ifHigh(() -> action.accept(alliance));
    }
}