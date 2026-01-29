package frc.robot.resources.pathplanner;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.json.simple.parser.ParseException;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PathFollowingController;
import com.pathplanner.lib.util.DriveFeedforwards;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.resources.gameinfo.GeneralGameInfoSupplier;
import frc.robot.subsystems.drivebase.AutoDrivebase;

class PathPlannerConfiguratorImpl implements PathPlannerConfigurator {
    private static final Alliance ORIGIN_ALLIANCE = Alliance.Blue;

    private final Supplier<Pose2d> getPose;
    private final Consumer<Pose2d> resetOdometry;
    private final Supplier<ChassisSpeeds> robotRelativeSpeedsSupplier;
    private final BiConsumer<ChassisSpeeds, DriveFeedforwards> output;
    private final PathFollowingController controller;
    private final GeneralGameInfoSupplier gameInfoSupplier;
    private final Subsystem drivebase;

    private boolean initialized;

    PathPlannerConfiguratorImpl(Supplier<Pose2d> getPose, Consumer<Pose2d> resetOdometry,
            Supplier<ChassisSpeeds> robotRelativeSpeedsSupplier,
            BiConsumer<ChassisSpeeds, DriveFeedforwards> output,
            PathFollowingController controller,
            GeneralGameInfoSupplier gameInfoSupplier, Subsystem drivebase) {
        this.getPose = getPose;
        this.resetOdometry = resetOdometry;
        this.robotRelativeSpeedsSupplier = robotRelativeSpeedsSupplier;
        this.output = output;
        this.controller = controller;
        this.gameInfoSupplier = gameInfoSupplier;
        this.drivebase = drivebase;
    }

    private static RobotConfig getRobotConfig() throws RobotConfigException {
        try {
            return RobotConfig.fromGUISettings();
        } catch (final IOException cause) {
            throw new RobotConfigException("Could not open robot config file!", cause);
        } catch (final ParseException cause) {
            throw new RobotConfigException("Could not parse robot config file!", cause);
        }
    }

    private void configure() throws PathPlannerConfigException {
        final RobotConfig robotConfig;

        try {
            robotConfig = getRobotConfig();
        } catch (final RobotConfigException cause) {
            throw new PathPlannerConfigException("Could not get robot config!", cause);
        }

        AutoBuilder.configure(getPose, resetOdometry, robotRelativeSpeedsSupplier, output,
                controller, robotConfig,
                () -> gameInfoSupplier.getAlliance().equals(ORIGIN_ALLIANCE), drivebase);
    }

    private void init() {

    }

    @Override
    public void configureAndInit() throws PathPlannerConfigException {
        if (!initialized) {
            configure();
            init();

            initialized = true;
        }
    }
}
