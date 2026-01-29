package frc.robot.subsystems.drivebase;

import java.util.Optional;
import frc.robot.resources.gameinfo.GeneralGameInfoSupplier;
import frc.robot.resources.pathplanner.PathPlannerConfigurator;
import frc.robot.resources.vision.VisionMeasurementsSupplier;

public interface Drivebase extends AutoDrivebase, ManualDrivebase, InputSupplier {
    Optional<PathPlannerConfigurator> getPathPlannerConfigurator();

    void setBrake(boolean brake);

    static Drivebase create(VisionMeasurementsSupplier vision, GeneralGameInfoSupplier gameInfoSupplier)
            throws DrivebaseInitException {
        return new SwerveDrivebase(vision, gameInfoSupplier);
    }
}
