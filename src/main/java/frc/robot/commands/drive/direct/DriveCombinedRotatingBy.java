package frc.robot.commands.drive.direct;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public final class DriveCombinedRotatingBy extends DriveCombined {
    public DriveCombinedRotatingBy(
        SemidirectDrivebase drivebase, 
        Rotation2d targetRotation, 
        Supplier<ChassisSpeeds> input
    ) {
        super(
            drivebase, 
            new RotationTarget(drivebase, targetRotation), 
            input
        );
    }
}
