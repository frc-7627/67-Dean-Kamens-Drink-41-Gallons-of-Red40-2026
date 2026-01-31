package frc.robot.commands.drive.auto.direct;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public class DriveDirect extends DriveDirectUntil {
    public DriveDirect(DirectDrivebase drivebase, Supplier<ChassisSpeeds> input, Class<?> cls) {
        super(drivebase, input, () -> false, cls);
    }

    public DriveDirect(DirectDrivebase drivebase, Supplier<ChassisSpeeds> input) {
        this(drivebase, input, DriveDirect.class);
    }
}
