package frc.robot.commands.drive.teleop.semidirect;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.commands.drive.auto.direct.DriveDirect;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveSemidirectRotatingBy extends DriveDirect {
    public DriveSemidirectRotatingBy(
        SemidirectDrivebase drivebase,
        Supplier<ChassisSpeeds> input,
        Rotation2d targetRotation
    ) {
        super(drivebase, getCombinedInput(drivebase, input, targetRotation));
    }

    private static Supplier<ChassisSpeeds> getCombinedInput(
        SemidirectDrivebase drivebase,
        Supplier<ChassisSpeeds> input,
        Rotation2d targetRotation
    ) {
        final Supplier<ChassisSpeeds> rotationControl = drivebase.getRotationControl(targetRotation);
        return () -> input.get().plus(rotationControl.get());
    }
}
