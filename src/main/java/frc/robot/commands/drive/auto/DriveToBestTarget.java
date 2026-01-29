package frc.robot.commands.drive.auto;

import frc.robot.subsystems.drivebase.AutoDrive;
import frc.robot.subsystems.offset.OffsetSupplier;
import frc.robot.subsystems.vision.BestTargetSupplier;

public class DriveToBestTarget extends DriveToOptionalPose {
    public DriveToBestTarget(AutoDrive drivebase, BestTargetSupplier vision,
            OffsetSupplier offsetSupplier) {
        super(drivebase, vision.getBestTargetPose().map(offsetSupplier::applyOffset));
    }
}
