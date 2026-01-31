package frc.robot.commands.drive.semiauto;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.SemiautoDrivebase;

public class DriveDirectlyOrientingTo extends DriveDirectlyOrienting {
    public DriveDirectlyOrientingTo(
        SemiautoDrivebase drivebase, 
        Supplier<ChassisSpeeds> input,
        Translation2d targetPosition
    ) {
        super(
            drivebase, 
            input,
            () -> targetPosition.minus(drivebase.getPose().getTranslation()).getAngle()
        );
    }
}
