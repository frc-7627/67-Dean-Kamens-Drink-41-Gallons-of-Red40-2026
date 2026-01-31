package frc.robot.subsystems.drivebase;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface SemiautoDrivebase extends TeleopDrivebase, AutoDrivebase {
    /**
     * Gets a controller that returns chassis speeds that rotate the robot to the target rotation.
     * 
     * @param targetRotation the target rotation from the initial pose when called.
     * @return a rotation controller.
     */
    Supplier<ChassisSpeeds> getRotationControl(Rotation2d targetRotation);

    /**
     * Gets a controller that returns chassis speeds that rotate the robot to the target
     * orientation.
     * 
     * @param targetOrientationSupplier gets the target orientation.
     * @return a rotation controller.
     */
    Supplier<ChassisSpeeds> getOrientationControl(Supplier<Rotation2d> targetOrientationSupplier);

    /**
     * @param targetOrientationSupplier gets the target orientation.
     * @return a supplier that checks whether the orientation of the robot has converged to the
     *         target orientation.
     */
    BooleanSupplier getOrientationConvergenceSupplier(
            Supplier<Rotation2d> targetOrientationSupplier);
}
