package frc.robot.commands.drive.auto;

import frc.robot.subsystems.drivebase.AutoDrivebase;
import frc.robot.subsystems.offset.OffsetSupplier;
import frc.robot.subsystems.vision.BestTargetSupplier;

public class DriveToBestTarget extends DriveToOptionalPose {
    public DriveToBestTarget(AutoDrivebase drivebase, BestTargetSupplier vision,
            OffsetSupplier offsetSupplier) {
        super(drivebase, vision.getBestTargetPose().map(offsetSupplier::applyOffset));
    }
}
