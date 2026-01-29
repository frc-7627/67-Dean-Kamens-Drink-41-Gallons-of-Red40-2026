package frc.robot.commands.drive.manual;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.ManualDrivebase;

public class DriveWithInput extends Command {
    private final ManualDrivebase drivebase;
    private final Supplier<ChassisSpeeds> input;

    public DriveWithInput(ManualDrivebase drivebase, Supplier<ChassisSpeeds> input) {
        this.drivebase = drivebase;
        this.input = input;
    }

    @Override
    public void execute() {
        drivebase.driveWithSpeeds(input.get());
    }
}
