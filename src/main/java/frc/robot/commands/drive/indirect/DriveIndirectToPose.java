package frc.robot.commands.drive.indirect;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import frc.robot.subsystems.drivebase.IndirectDrivebase;

class DriveIndirectToPose extends WrapperCommand {
    DriveIndirectToPose(IndirectDrivebase drivebase, Pose2d targetPose) {
        super(AutoBuilder.pathfindToPose(targetPose, drivebase.getPathConstraints()));
    }
}
