package frc.robot.resources.gameinfo;

import java.util.Optional;
import java.util.logging.Logger;
import com.fasterxml.jackson.core.TreeNode;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
import frc.robot.Constants;
import frc.robotlib.resource.SharedResourceBase;

final class GameInfoSupplierImpl extends SharedResourceBase implements GameInfoSupplier {
    private static final Logger LOGGER = Logger.getLogger(GameInfoSupplier.class.getSimpleName());

    private final EventLoop eventLoop = new EventLoop();
    private final BooleanEvent allianceSetEvent;
    private Phase phase = Constants.GameInfoConstants.START_PHASE;
    private Alliance alliance = Constants.GameInfoConstants.DEFAULT_ALLIANCE;
    private boolean isDistinctAlliance = false;
    private boolean hasGotAlliance = false;

    /**
     * Resource for getting game info.
     */
    GameInfoSupplierImpl() {
        BooleanEvent isDistinctAllianceEvent =
                new BooleanEvent(eventLoop, () -> isDistinctAlliance);
        BooleanEvent hasGotAllianceEvent = new BooleanEvent(eventLoop, () -> hasGotAlliance);
        this.allianceSetEvent = isDistinctAllianceEvent.or(hasGotAllianceEvent.rising());

        allianceSetEvent.ifHigh(() -> {
            LOGGER.info("Alliance set from driver station.");
        });
    }

    @Override
    public Phase getPhase() {
        return phase;
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
            hasGotAlliance = true;

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
    public void onAllianceSet(Runnable action) {
        allianceSetEvent.ifHigh(action);
    }

    @Override
    public boolean isHubActive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isHubActive'");
    }

    @Override
    public Translation2d getHubPose() {
        Translation2d Hub = new Translation2d();
        return switch (alliance) {
            case Red -> Hub = Translation2d.kZero.minus(Constants.VisionConstants.HubOffset);
            case Blue -> Translation2d.kZero.plus(Constants.VisionConstants.HubOffset);
        };
    }
}
