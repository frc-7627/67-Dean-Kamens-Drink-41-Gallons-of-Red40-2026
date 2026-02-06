package frc.robot.commands.drive.direct;

import frc.robot.subsystems.drivebase.SemidirectDrivebase;

abstract class DriveAngular extends DriveSemidirect {
    protected DriveAngular(SemidirectDrivebase drivebase, OrientationTarget target) {
        super(drivebase, target);
    }
}
