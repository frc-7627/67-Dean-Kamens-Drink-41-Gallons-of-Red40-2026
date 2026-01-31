package frc.robot.commands.drive.auto.semidirect;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.drive.auto.direct.DriveDirectUntil;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

class DriveSemidirectUntilOriented extends DriveDirectUntil {
    DriveSemidirectUntilOriented(
        SemidirectDrivebase drivebase, 
        Supplier<Rotation2d> targetOrientationSupplier,
        Class<?> cls
    ) {
        super(
            drivebase, 
            drivebase.getOrientationControl(targetOrientationSupplier), 
            drivebase.getOrientationConvergenceSupplier(targetOrientationSupplier),
            cls
        );
    }

    DriveSemidirectUntilOriented(
        SemidirectDrivebase drivebase, 
        Supplier<Rotation2d> targetOrientationSupplier
    ) {
        this(drivebase, targetOrientationSupplier, DriveSemidirectUntilOriented.class);
    }
}
