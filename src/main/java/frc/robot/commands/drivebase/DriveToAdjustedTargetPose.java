package frc.robot.commands.drivebase;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.drivebase.PathConstraintsSupplier;
import frc.robot.subsystems.vision.BestTargetSupplier;

public class DriveToAdjustedTargetPose extends DriveToPose {
    public DriveToAdjustedTargetPose(PathConstraintsSupplier drivebase,
            BestTargetSupplier vision, Translation2d offset) {
        super(drivebase, vision.getBestTargetPose()
                .map(pose -> pose.transformBy(new Transform2d(offset, new Rotation2d()))));
    }
}
