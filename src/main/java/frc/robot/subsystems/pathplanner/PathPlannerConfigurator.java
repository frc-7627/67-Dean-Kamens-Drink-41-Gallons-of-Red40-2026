package frc.robot.subsystems.pathplanner;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.pathplanner.lib.controllers.PathFollowingController;
import com.pathplanner.lib.util.DriveFeedforwards;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.subsystem.SharedSubsystem;
import frc.robot.subsystems.gameinfo.GeneralGameInfoSupplier;

/**
 * Interface that plans and creates the path for the robot given its current
 * state.
 */
public interface PathPlannerConfigurator extends SharedSubsystem {
    void configureAndInit() throws PathPlannerConfigException;

    static PathPlannerConfigurator create(Supplier<Pose2d> getPose, Consumer<Pose2d> resetOdometry,
            Supplier<ChassisSpeeds> robotRelativeSpeedsSupplier,
            BiConsumer<ChassisSpeeds, DriveFeedforwards> output, PathFollowingController controller,
            GeneralGameInfoSupplier gameInfoSupplier, Subsystem drivebase) {
        return new PathPlannerConfiguratorImpl(getPose, resetOdometry, robotRelativeSpeedsSupplier,
                output, controller, gameInfoSupplier, drivebase);
    }
}
