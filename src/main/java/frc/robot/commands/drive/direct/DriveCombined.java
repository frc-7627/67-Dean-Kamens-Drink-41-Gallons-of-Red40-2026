package frc.robot.commands.drive.direct;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

abstract class DriveCombined extends DriveSemidirect {
    protected DriveCombined(
        SemidirectDrivebase drivebase, 
        OrientationTarget target, 
        Supplier<ChassisSpeeds> input) 
    {
        super(drivebase, target, input);
    }

    @Override
    public final boolean isFinished() {
        return false;
    }
}
