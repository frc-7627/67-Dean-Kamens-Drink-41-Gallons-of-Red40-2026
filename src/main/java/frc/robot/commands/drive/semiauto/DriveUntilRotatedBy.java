package frc.robot.commands.drive.semiauto;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.drive.teleop.DriveDirectlyWhile;
import frc.robot.subsystems.drivebase.SemiautoDrivebase;

public class DriveUntilRotatedBy extends DriveDirectlyWhile {
    public DriveUntilRotatedBy(SemiautoDrivebase drivebase, Rotation2d targetRotation) {
        super(
            drivebase,
            drivebase.getRotationControl(targetRotation),
            drivebase.getRotationConvergenceSupplier(targetRotation)
        );
    }
}
