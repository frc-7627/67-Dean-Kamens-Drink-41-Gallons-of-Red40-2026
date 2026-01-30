package frc.robot.commands.drive.auto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.drivebase.AutoDrivebase;

public class RotateCCW90Deg extends DriveToTransformed {
    public RotateCCW90Deg(AutoDrivebase drivebase) {
        super(drivebase, new Transform2d(new Translation2d(), Rotation2d.kCCW_90deg));
    }
}
