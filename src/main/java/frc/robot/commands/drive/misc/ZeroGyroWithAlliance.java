package frc.robot.commands.drive.misc;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.controllable.drivebase.MiscDrivebase;

public class ZeroGyroWithAlliance extends InstantCommand {
    public ZeroGyroWithAlliance(MiscDrivebase drivebase) {
        super(drivebase::zeroGyroWithAlliance, drivebase);
    }
}
