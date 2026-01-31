package frc.robot.commands.drive.semidirect;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.drive.direct.DriveDirectlyWhile;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveUntilRotatedBy extends DriveDirectlyWhile {
    public DriveUntilRotatedBy(SemidirectDrivebase drivebase, Rotation2d targetRotation) {
        super(
            drivebase,
            drivebase.getRotationControl(targetRotation),
            drivebase.getRotationConvergenceSupplier(targetRotation)
        );
    }
}
