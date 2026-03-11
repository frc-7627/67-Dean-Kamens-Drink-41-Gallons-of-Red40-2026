package frc.robot.subsystems.shared.gameinfo;

import static frc.robot.Constants.DrivebaseConstants.BLUE_LEFT_FERRY_TARGET_POSITION;
import static frc.robot.Constants.DrivebaseConstants.BLUE_RIGHT_FERRY_TARGET_POSITION;
import static frc.robot.Constants.DrivebaseConstants.RED_LEFT_FERRY_TARGET_POSITION;
import static frc.robot.Constants.DrivebaseConstants.RED_RIGHT_FERRY_TARGET_POSITION;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.Odometry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
import frc.bofalib.subsystem.SharedSubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.controllable.drivebase.Side;
import swervelib.SwerveDrive;

final class GameInfoSupplierImpl extends SharedSubsystemBase implements GameInfoSupplier {
    private static final Logger LOGGER = Logger.getLogger(GameInfoSupplier.class.getName());

    String gameData = DriverStation.getGameSpecificMessage();
    private final EventLoop eventLoop = new EventLoop();
    private final BooleanEvent allianceSetEvent;
    private Phase phase = Constants.GameInfoConstants.START_PHASE;
    private Alliance alliance;
    private boolean isDistinctAlliance = false;
    private final Timer phaseTimer = new Timer();

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
    }

    @Override
    public Phase getPhase() {

        if (phaseTimer.hasElapsed(110)) {
            phase = Phase.ENDGAME;
        } else if (phaseTimer.hasElapsed(85)) {
            phase = Phase.TELEOP_4;
        } else if (phaseTimer.hasElapsed(60)) {
            phase = Phase.TELEOP_3;
        } else if (phaseTimer.hasElapsed(35)) {
            phase = Phase.TELEOP_2;
        } else if (phaseTimer.hasElapsed(10)) {
            phase = Phase.TELEOP_1;
        } else {
            phase = Phase.TRANSITION;
        }

        return phase;
    }

    @Override
    public void teleopInit() {
        phaseTimer.start();
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
        Phase currentPhase = getPhase();
        return switch (currentPhase) {
            case AUTO, TRANSITION, ENDGAME -> true;
            case TELEOP_1, TELEOP_3 -> gameData.charAt(0) == 'B';
            case TELEOP_2, TELEOP_4 -> gameData.charAt(0) == 'R';
        };
    }

    @Override
    public Translation2d getHubPosition() {
        // System.out.println("Getting the Hub pose for the respective Alliance");
        return switch (alliance) {
            case Red -> Constants.VisionConstants.RED_HUB_LOCATION;
            case Blue -> Constants.VisionConstants.BLUE_HUB_LOCATION;
        };
    }

    @Override
    public boolean willHubActivate() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Translation2d getFerryTargetPosition(Side side) {
        return switch (side) {
            case LEFT -> switch (alliance) {
                case Red -> RED_LEFT_FERRY_TARGET_POSITION;
                case Blue -> BLUE_LEFT_FERRY_TARGET_POSITION;
            };
            case RIGHT -> switch (alliance) {
                case Red -> RED_RIGHT_FERRY_TARGET_POSITION;
                case Blue -> BLUE_RIGHT_FERRY_TARGET_POSITION;
            };
        };
    }
}
