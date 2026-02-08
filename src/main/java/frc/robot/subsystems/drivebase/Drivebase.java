package frc.robot.subsystems.drivebase;

import java.util.Optional;
import frc.robot.subsystems.gameinfo.GeneralGameInfoSupplier;
import frc.robot.subsystems.pathplanner.PathPlannerConfigurator;
import frc.robot.subsystems.vision.VisionMeasurementsSupplier;

/**
 * Interface keeping track of all of the robot's driving functions and parts.
 */
public interface Drivebase extends 
    IndirectDrivebase, 
    SemidirectDrivebase, 
    MiscDrivebase, 
    InputSupplier,
    KinematicSupplier
{
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
