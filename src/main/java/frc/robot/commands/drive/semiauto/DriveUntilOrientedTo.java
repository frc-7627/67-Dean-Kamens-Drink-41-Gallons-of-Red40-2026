package frc.robot.commands.drive.semiauto;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.drivebase.SemiautoDrivebase;

public class DriveUntilOrientedTo extends DriveUntilOriented {
    public DriveUntilOrientedTo(SemiautoDrivebase drivebase, Translation2d targetPosition) {
        super(
            drivebase,
            () -> targetPosition.minus(drivebase.getPose().getTranslation()).getAngle()
        );
    }
}
