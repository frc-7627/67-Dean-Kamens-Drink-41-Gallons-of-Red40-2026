package frc.robot.commands.drive.auto.semidirect;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.drive.auto.direct.DriveDirectUntil;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveSemidirectUntilRotatedBy extends DriveDirectUntil {
    public DriveSemidirectUntilRotatedBy(SemidirectDrivebase drivebase, Rotation2d targetRotation) {
        super(
            drivebase,
            drivebase.getRotationControl(targetRotation),
            drivebase.getRotationConvergenceSupplier(targetRotation)
        );
    }
}
