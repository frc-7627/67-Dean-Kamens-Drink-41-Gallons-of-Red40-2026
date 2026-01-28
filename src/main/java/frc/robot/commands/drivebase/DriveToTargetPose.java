package frc.robot.commands.drivebase;

import frc.robot.subsystems.drivebase.PathConstraintsSupplier;
import frc.robot.subsystems.vision.BestTargetSupplier;

public class DriveToTargetPose extends DriveToPose {
    public DriveToTargetPose(PathConstraintsSupplier drivebase,
            BestTargetSupplier vision) {
        super(drivebase, vision.getBestTargetPose());
    }
}
