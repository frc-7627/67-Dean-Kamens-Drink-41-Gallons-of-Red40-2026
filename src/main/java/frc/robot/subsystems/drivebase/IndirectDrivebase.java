package frc.robot.subsystems.drivebase;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface IndirectDrivebase extends Subsystem, PoseSupplier {
    PathConstraints getPathConstraints();
}
