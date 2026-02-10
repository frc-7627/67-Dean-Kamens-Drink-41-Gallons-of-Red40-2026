package frc.robot.subsystems.drivebase;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.json.simple.parser.ParseException;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.controllers.PathFollowingController;
import com.pathplanner.lib.util.DriveFeedforwards;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Subsystem;

final class PathPlannerConfig {
    private static final class PathPlannerConfigError extends Error {
        PathPlannerConfigError(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    private static final PathFollowingController CONTROLLER = new PPHolonomicDriveController(
        // Translation PID Constants
        new PIDConstants(5.0, 0.0, 0.0),
        // Rotation PID Constants
        new PIDConstants(5.0, 0.0, 0.0)
    );

    private static final Alliance ORIGIN_ALLIANCE = Alliance.Blue;

    private static boolean configured = false;


    private static BooleanSupplier shouldFlipPath(Supplier<Alliance> allianceSupplier) {
        return () -> allianceSupplier.get().equals(ORIGIN_ALLIANCE);
    }

    private PathPlannerConfig() {}

    private static RobotConfig getRobotConfig() {
        try {
            return RobotConfig.fromGUISettings();
        } catch (final IOException cause) {
            throw new PathPlannerConfigError("Could not open robot config file!", cause);
        } catch (final ParseException cause) {
            throw new PathPlannerConfigError("Could not parse robot config file!", cause);
        }
    }

    static void configure(
        Supplier<Pose2d> poseSupplier, 
        Consumer<Pose2d> odometryResetter,
        Supplier<ChassisSpeeds> robotRelativeSpeedsSupplier,
        BiConsumer<ChassisSpeeds, DriveFeedforwards> robotRelativeWithFeedforwardsDriver,
        Supplier<Alliance> allianceSupplier, 
        Subsystem drivebaseSubsystem
    ) {
        if (!configured) {
            AutoBuilder.configure(
                poseSupplier, 
                odometryResetter,
                robotRelativeSpeedsSupplier, 
                robotRelativeWithFeedforwardsDriver, 
                CONTROLLER, 
                getRobotConfig(), 
                shouldFlipPath(allianceSupplier), 
                drivebaseSubsystem
            );

            configured = true;
        }
    }
}
