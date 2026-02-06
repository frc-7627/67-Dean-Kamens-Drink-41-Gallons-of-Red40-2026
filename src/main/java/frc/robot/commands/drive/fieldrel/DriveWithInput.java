package frc.robot.commands.drive.fieldrel;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.FieldRelativeDrivebase;

public final class DriveWithInput extends Command {
    private final FieldRelativeDrivebase drivebase;
    private final Supplier<ChassisSpeeds> input;

    public DriveWithInput(
        FieldRelativeDrivebase drivebase, 
        Supplier<ChassisSpeeds> input
    ) {
        this.drivebase = drivebase;
        this.input = input;

        addRequirements(drivebase);
    }

    @Override
    public void execute() {
        drivebase.driveWithFieldRelativeSpeeds(input.get());
    }
}
