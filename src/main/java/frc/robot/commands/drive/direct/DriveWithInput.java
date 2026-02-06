package frc.robot.commands.drive.direct;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public final class DriveWithInput extends Command {
    private final DirectDrivebase drivebase;
    private final Supplier<ChassisSpeeds> input;

    public DriveWithInput(DirectDrivebase drivebase, Supplier<ChassisSpeeds> input) {
        this.drivebase = drivebase;
        this.input = input;
    }

    @Override
    public void execute() {
        drivebase.driveWithFieldRelativeSpeeds(input.get());
    }
}
