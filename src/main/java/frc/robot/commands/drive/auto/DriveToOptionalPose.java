package frc.robot.commands.drive.auto;

import java.util.Optional;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import frc.robot.subsystems.drivebase.AutoDrivebase;

class DriveToOptionalPose extends WrapperCommand {
    DriveToOptionalPose(AutoDrivebase drivebase, Optional<Pose2d> poseOptional) {
        super(poseOptional.isPresent()
                ? AutoBuilder.pathfindToPose(poseOptional.get(), drivebase.getPathConstraints())
                : Commands.none());

        addRequirements(drivebase);
    }
}
