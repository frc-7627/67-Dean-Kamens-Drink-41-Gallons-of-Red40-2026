package frc.robot.commands.drive.auto.semidirect;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveUntilOrientedTo extends DriveUntilOriented {
    public DriveUntilOrientedTo(SemidirectDrivebase drivebase, Translation2d targetPosition) {
        super(
            drivebase,
            () -> targetPosition.minus(drivebase.getPose().getTranslation()).getAngle()
        );
    }
}
