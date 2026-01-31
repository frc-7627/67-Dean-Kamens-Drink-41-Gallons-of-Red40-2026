package frc.robot.commands.drive.semiauto;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.drive.teleop.DriveDirectlyWhile;
import frc.robot.subsystems.drivebase.SemiautoDrivebase;

class DriveUntilOriented extends DriveDirectlyWhile {
    DriveUntilOriented(
        SemiautoDrivebase drivebase, 
        Supplier<Rotation2d> targetOrientationSupplier
    ) {
        super(
            drivebase, 
            drivebase.getOrientationControl(targetOrientationSupplier), 
            drivebase.getOrientationConvergenceSupplier(targetOrientationSupplier)
        );
    }
}
