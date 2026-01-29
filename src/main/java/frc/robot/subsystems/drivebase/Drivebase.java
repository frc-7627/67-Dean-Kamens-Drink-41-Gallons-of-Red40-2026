package frc.robot.subsystems.drivebase;

import java.util.Optional;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.resources.pathplanner.PathPlannerConfigurator;
import frc.robot.resources.vision.VisionMeasurementsSupplier;

public interface Drivebase extends AutoDrivebase, ManualDrivebase, InputSupplier {
    Optional<PathPlannerConfigurator> getPathPlannerConfigurator();

    static Drivebase create(VisionMeasurementsSupplier vision, Alliance alliance)
            throws DrivebaseInitException {
        return new SwerveDrivebase(vision, alliance);
    }
}
