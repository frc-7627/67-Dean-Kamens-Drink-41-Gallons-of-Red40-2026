package frc.robot.commands.drive.teleop;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.drivebase.TeleopDrivebase;

public class DriveDirectly extends RunCommand {
    public DriveDirectly(TeleopDrivebase drivebase, Supplier<ChassisSpeeds> input) {
        super(() -> drivebase.driveWithSpeeds(input.get()), drivebase);
    }
}
