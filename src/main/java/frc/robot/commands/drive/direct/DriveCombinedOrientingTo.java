package frc.robot.commands.drive.direct;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public final class DriveCombinedOrientingTo extends DriveSemidirect {
    public DriveCombinedOrientingTo(
        SemidirectDrivebase drivebase, 
        Translation2d targetLocation, 
        Supplier<ChassisSpeeds> input
    ) {
        super(
            drivebase, 
            new LocationTarget(drivebase, targetLocation), 
            input
        );
    }
}
