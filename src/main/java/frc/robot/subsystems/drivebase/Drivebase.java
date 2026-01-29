package frc.robot.subsystems.drivebase;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.vision.VisionMeasurementsSupplier;

public interface Drivebase extends AutoDrivebase, ManualDrivebase {
    static Drivebase create(VisionMeasurementsSupplier vision, Alliance alliance)
            throws DrivebaseConstructorException {
        return new SwerveDrivebase(vision, alliance);
    }
}
