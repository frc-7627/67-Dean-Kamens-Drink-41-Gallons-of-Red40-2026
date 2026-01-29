package frc.robot.commands.drive.auto;

import java.util.Optional;
import edu.wpi.first.math.geometry.Transform2d;
import frc.robot.subsystems.drivebase.AutoDrivebase;

class DriveToTransformed extends DriveToOptionalPose {
    DriveToTransformed(AutoDrivebase drivebase, Transform2d transform) {
        super(drivebase, Optional.of(drivebase.getPose().transformBy(transform)));
    }
}
