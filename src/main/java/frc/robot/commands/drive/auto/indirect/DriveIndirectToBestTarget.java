package frc.robot.commands.drive.auto.indirect;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import frc.robot.resources.vision.BestTargetSupplier;
import frc.robot.subsystems.drivebase.IndirectDrivebase;

public class DriveIndirectToBestTarget extends WrapperCommand {
    public DriveIndirectToBestTarget(IndirectDrivebase drivebase, BestTargetSupplier vision) {
        super(vision.getBestTargetPose()
            .map(targetPose -> (Command) new DriveIndirectToPose(drivebase, targetPose))
            .orElse(Commands.none())
        );
    }
}
