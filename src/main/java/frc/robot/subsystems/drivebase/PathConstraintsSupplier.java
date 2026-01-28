package frc.robot.subsystems.drivebase;

import com.pathplanner.lib.path.PathConstraints;

public interface PathConstraintsSupplier {
    PathConstraints getPathConstraints();
}
