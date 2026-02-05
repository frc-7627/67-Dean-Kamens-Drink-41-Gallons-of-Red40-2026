package frc.robot.commands.drive.direct;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.DirectDrivebase;

abstract class DriveDirect extends Command {
    private final DirectDrivebase drivebase;

    protected DriveDirect(DirectDrivebase drivebase) {
        this.drivebase = drivebase;

        addRequirements(drivebase);
    }

    abstract protected ChassisSpeeds getSpeeds();
    
    @Override
    public final void execute() {
        drivebase.driveWithSpeeds(getSpeeds());
    }
}
