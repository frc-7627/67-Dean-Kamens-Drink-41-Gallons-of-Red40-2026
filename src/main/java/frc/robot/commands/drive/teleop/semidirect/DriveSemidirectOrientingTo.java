package frc.robot.commands.drive.teleop.semidirect;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

public class DriveSemidirectOrientingTo extends DriveSemidirectOrienting {
    public DriveSemidirectOrientingTo(
        SemidirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> input,
        Translation2d targetPosition
    ) {
        super(
            drivebase, 
            input,
            () -> targetPosition.minus(drivebase.getPose().getTranslation()).getAngle(),
            DriveSemidirectOrientingTo.class
        );
    }
}
