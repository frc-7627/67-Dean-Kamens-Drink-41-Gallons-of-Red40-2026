package frc.robot.commands.drive.teleop.semidirect;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.commands.drive.auto.direct.DriveDirect;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

class DriveSemidirectOrienting extends DriveDirect {
    DriveSemidirectOrienting(
        SemidirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> input, 
        Supplier<Rotation2d> targetOrientationSupplier
    ) {
        super(drivebase, getCombinedInput(drivebase, input, targetOrientationSupplier));
    }

    private static Supplier<ChassisSpeeds> getCombinedInput(
        SemidirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> input, 
        Supplier<Rotation2d> targetOrientationSupplier
    ) {
        final Supplier<ChassisSpeeds> orientationControl = 
            drivebase.getOrientationControl(targetOrientationSupplier);
        return () -> input.get().plus(orientationControl.get());
    }
}
