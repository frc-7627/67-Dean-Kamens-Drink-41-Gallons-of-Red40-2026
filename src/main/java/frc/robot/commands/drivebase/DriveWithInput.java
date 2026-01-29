package frc.robot.commands.drivebase;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.ManualDrive;

public class DriveWithInput extends Command {
    private final ManualDrive drivebase;
    private final Supplier<ChassisSpeeds> input;

    public DriveWithInput(ManualDrive drivebase, Supplier<ChassisSpeeds> input) {
        this.drivebase = drivebase;
        this.input = input;
    }

    @Override
    public void execute() {
        drivebase.driveWithSpeeds(input.get());
    }
}
