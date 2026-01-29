package frc.robot.commands.drivebase;

import frc.robot.subsystems.drivebase.PathConstraintsSupplier;
import frc.robot.subsystems.offset.OffsetSupplier;
import frc.robot.subsystems.vision.BestTargetSupplier;

public class DriveToBestTarget extends DriveToPose {
    public DriveToBestTarget(PathConstraintsSupplier drivebase, BestTargetSupplier vision,
            OffsetSupplier offsetSupplier) {
        super(drivebase, vision.getBestTargetPose().map(offsetSupplier::applyOffset));
    }
}
