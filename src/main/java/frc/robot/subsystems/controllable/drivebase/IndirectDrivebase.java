package frc.robot.subsystems.controllable.drivebase;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface containing the constraints/limits when motion planning.
 */
public interface IndirectDrivebase extends Subsystem {
    PathConstraints getPathConstraints();
}
