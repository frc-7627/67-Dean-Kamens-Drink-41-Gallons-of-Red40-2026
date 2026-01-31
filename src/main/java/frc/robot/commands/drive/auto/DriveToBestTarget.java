package frc.robot.commands.drive.auto;

import frc.robot.resources.vision.BestTargetSupplier;
import frc.robot.subsystems.drivebase.AutoDrivebase;

public class DriveToBestTarget extends DriveToOptionalPose {
    public DriveToBestTarget(AutoDrivebase drivebase, BestTargetSupplier vision) {
        super(drivebase, vision.getBestTargetPose());
    }
}
