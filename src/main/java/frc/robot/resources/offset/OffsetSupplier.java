package frc.robot.resources.offset;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robotlib.resource.SharedResource;

/**
 * Interface that supplies the 2D offsets to the robot.
 */
public interface OffsetSupplier extends SharedResource {
    Translation2d getOffset();

    default Pose2d applyOffset(Pose2d pose) {
        return pose.transformBy(new Transform2d(getOffset(), new Rotation2d()));
    }

    static OffsetSupplier create() {
        return new OffsetSupplierImpl();
    }
}
