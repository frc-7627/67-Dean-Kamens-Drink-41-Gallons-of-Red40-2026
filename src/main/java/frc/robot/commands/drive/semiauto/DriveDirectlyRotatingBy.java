package frc.robot.commands.drive.semiauto;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.commands.drive.teleop.DriveDirectly;
import frc.robot.subsystems.drivebase.SemiautoDrivebase;

public class DriveDirectlyRotatingBy extends DriveDirectly {
    public DriveDirectlyRotatingBy(
        SemiautoDrivebase drivebase,
        Supplier<ChassisSpeeds> input,
        Rotation2d targetRotation
    ) {
        super(drivebase, getCombinedInput(drivebase, input, targetRotation));
    }

    private static Supplier<ChassisSpeeds> getCombinedInput(
        SemiautoDrivebase drivebase,
        Supplier<ChassisSpeeds> input,
        Rotation2d targetRotation
    ) {
        final Supplier<ChassisSpeeds> rotationControl = drivebase.getRotationControl(targetRotation);
        return () -> input.get().plus(rotationControl.get());
    }
}
