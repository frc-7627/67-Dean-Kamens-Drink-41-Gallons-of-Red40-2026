package frc.robot.commands.drive.auto.indirect;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.commands.LoggingWrapperCommand;
import frc.robot.subsystems.drivebase.IndirectDrivebase;

class DriveIndirectToPose extends LoggingWrapperCommand {
    DriveIndirectToPose(IndirectDrivebase drivebase, Pose2d targetPose) {
        super(AutoBuilder.pathfindToPose(targetPose, drivebase.getPathConstraints()), DriveIndirectToPose.class);

        addRequirements(drivebase);
    }
}
