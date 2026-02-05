package frc.robot.commands.drive.direct;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public final class DriveAngularRotatingBy extends DriveAngular {
    public DriveAngularRotatingBy(SemidirectDrivebase drivebase, Rotation2d targetRotation) {
        super(
            drivebase, 
            new RotationTarget(drivebase, targetRotation)
        );
    }
}
