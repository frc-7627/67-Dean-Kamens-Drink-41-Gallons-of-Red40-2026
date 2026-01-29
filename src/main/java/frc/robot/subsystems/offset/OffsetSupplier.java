package frc.robot.subsystems.offset;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;

public interface OffsetSupplier {
    Translation2d getOffset();

    default Pose2d applyOffset(Pose2d pose) {
        return pose.transformBy(new Transform2d(getOffset(), new Rotation2d()));
    }

    static OffsetSupplier create() {
        return new OffsetSupplierImpl();
    }
}
