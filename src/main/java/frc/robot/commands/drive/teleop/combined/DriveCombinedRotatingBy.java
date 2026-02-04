package frc.robot.commands.drive.teleop.combined;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.AngularControl;
import frc.robot.subsystems.drivebase.DirectDrivebase;
import frc.robot.subsystems.drivebase.KinematicSupplier;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveCombinedRotatingBy extends DriveCombinedOrienting {
    public DriveCombinedRotatingBy(
        SemidirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> translationInput,
        Rotation2d rotation
    ) {
        super(
            drivebase, 
            translationInput,
            getTargetOrientationSupplier(drivebase.getPose().getRotation(), rotation)
        );
    }

    private static Supplier<Rotation2d> getTargetOrientationSupplier(
        Rotation2d initialOrientation,
        Rotation2d rotation
    ) {
        final Rotation2d targetOrientation = initialOrientation.plus(rotation);
        return () -> targetOrientation;
    }
}
