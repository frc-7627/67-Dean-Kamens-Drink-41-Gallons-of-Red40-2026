package frc.robot.commands.drive.auto;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.resources.gameinfo.SpecificGameInfoSupplier;
import frc.robot.subsystems.drivebase.AutoDrivebase;

public class FaceHub extends DriveToTransformed {
    public FaceHub(AutoDrivebase drivebase, SpecificGameInfoSupplier gameInfoSupplier) {
        super(drivebase,
            new Transform2d(Translation2d.kZero,
                gameInfoSupplier.getHubPose().minus(drivebase.getPose().getTranslation()).getAngle()
                .minus(drivebase.getPose().getRotation())));
    }


}
