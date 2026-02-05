package frc.robot.subsystems.drivebase;

/**
 * Interface managing the rotation of the robot along with the motion.
 */
public interface SemidirectDrivebase extends DirectDrivebase, KinematicSupplier {
    AngularControl getAngularControl();

    
}
