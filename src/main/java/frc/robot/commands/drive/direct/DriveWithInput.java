package frc.robot.commands.drive.direct;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public final class DriveWithInput extends DriveDirect {
    private final Supplier<ChassisSpeeds> input;

    public DriveWithInput(
        DirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> input
    ) {
        super(drivebase);

        this.input = input;
    }

    @Override
    protected ChassisSpeeds getSpeeds() {
        return input.get();
    }
}
