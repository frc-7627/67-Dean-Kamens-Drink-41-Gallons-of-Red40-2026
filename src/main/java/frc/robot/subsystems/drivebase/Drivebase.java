package frc.robot.subsystems.drivebase;

import java.util.Optional;
import frc.robot.resources.gameinfo.GeneralGameInfoSupplier;
import frc.robot.resources.pathplanner.PathPlannerConfigurator;
import frc.robot.resources.vision.VisionMeasurementsSupplier;

/**
 * Interface keeping track of all of the robot's driving functions and parts.
 */
public interface Drivebase extends IndirectDrivebase, SemidirectDrivebase, InputSupplier {
    Optional<PathPlannerConfigurator> getPathPlannerConfigurator();

    /**
     * Sets the motor mode to brake
     * 
     * @param brake is the brake on or off
     */
    void setBrake(boolean brake);

    static Drivebase create(VisionMeasurementsSupplier vision,
            GeneralGameInfoSupplier gameInfoSupplier) throws DrivebaseInitException {
        return new SwerveDrivebase(vision, gameInfoSupplier);
    }
}
