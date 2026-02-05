package frc.robot.commands.drive.direct;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public final class DriveAngularOrientingTo extends DriveAngular {
    public DriveAngularOrientingTo(SemidirectDrivebase drivebase, Translation2d targetLocation) {
        super(
            drivebase, 
            new LocationTarget(drivebase, targetLocation)
        );
    }
}
