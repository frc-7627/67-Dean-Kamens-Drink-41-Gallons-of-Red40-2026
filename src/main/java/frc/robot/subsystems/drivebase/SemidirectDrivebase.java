package frc.robot.subsystems.drivebase;

public interface SemidirectDrivebase extends DirectDrivebase, KinematicSupplier {
    AngularControl getAngularControl();
}
