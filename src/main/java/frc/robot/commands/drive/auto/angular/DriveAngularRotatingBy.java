package frc.robot.commands.drive.auto.angular;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveAngularRotatingBy extends DriveAngularOrienting {
    public DriveAngularRotatingBy(
        SemidirectDrivebase drivebase,
        Rotation2d rotation
    ) {
        super(
            drivebase, 
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
