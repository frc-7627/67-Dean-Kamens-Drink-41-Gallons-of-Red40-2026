package frc.robot.commands.drive.teleop.combined;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveCombinedOrientingTo extends DriveCombinedOrienting {
    public DriveCombinedOrientingTo(
        SemidirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> translationInput,
        Supplier<Translation2d> targetLocationSupplier
    ) {
        super(
            drivebase,
            translationInput,
            () -> targetLocationSupplier.get()
                .minus(drivebase.getPose().getTranslation())
                .getAngle()
        );
    }
}
