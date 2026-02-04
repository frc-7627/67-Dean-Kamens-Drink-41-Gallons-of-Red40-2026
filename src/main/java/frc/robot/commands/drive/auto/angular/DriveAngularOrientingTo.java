package frc.robot.commands.drive.auto.angular;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.drivebase.AngularControl;
import frc.robot.subsystems.drivebase.DirectDrivebase;
import frc.robot.subsystems.drivebase.KinematicSupplier;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveAngularOrientingTo extends DriveAngularOrienting {
    public DriveAngularOrientingTo(
        SemidirectDrivebase drivebase,
        Supplier<Translation2d> targetLocationSupplier
    ) {
        super(
            drivebase,
            () -> targetLocationSupplier.get()
                .minus(drivebase.getPose().getTranslation())
                .getAngle()
        );
    }
}
