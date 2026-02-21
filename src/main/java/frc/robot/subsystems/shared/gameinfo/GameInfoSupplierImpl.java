package frc.robot.subsystems.shared.gameinfo;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
import frc.bofalib.subsystem.SharedSubsystemBase;
import frc.robot.Constants;

final class GameInfoSupplierImpl extends SharedSubsystemBase implements GameInfoSupplier {
    private static final Logger LOGGER = Logger.getLogger(GameInfoSupplier.class.getName());

    String gameData = DriverStation.getGameSpecificMessage();
    private final EventLoop eventLoop = new EventLoop();
    private final BooleanEvent allianceSetEvent;
    private Phase phase = Constants.GameInfoConstants.START_PHASE;
    private Alliance alliance;
    private boolean isDistinctAlliance = false;

    /**
     * Resource for getting game info.
     */
    GameInfoSupplierImpl() {
        this.allianceSetEvent = new BooleanEvent(eventLoop, () -> isDistinctAlliance);

        this.alliance = DriverStation.getAlliance().orElse(
            Constants.GameInfoConstants.DEFAULT_ALLIANCE
        );

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

    @Override
    public boolean isHubActive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Translation2d getHubPosition() {
        //System.out.println("Getting the Hub pose for the respective Alliance");
        return switch (alliance) {
            case Red -> Constants.VisionConstants.HUB_LOCATION.unaryMinus();
            case Blue -> Constants.VisionConstants.HUB_LOCATION;
        };
    }

    @Override
    public boolean willHubActivate() {
        // TODO Auto-generated method stub
        return false;
    }
}
