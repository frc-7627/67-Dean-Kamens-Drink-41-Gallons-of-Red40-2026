package frc.robot.commands.drivebase;

import java.util.Optional;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import frc.robot.subsystems.drivebase.AutoDrive;

public class DriveToPose extends WrapperCommand {
    public DriveToPose(AutoDrive drivebase,
            Optional<Pose2d> targetPoseOptional) {
        super(targetPoseOptional.isPresent() ? AutoBuilder.pathfindToPose(targetPoseOptional.get(),
                drivebase.getPathConstraints()) : Commands.none());

        addRequirements(drivebase);
    }
}
