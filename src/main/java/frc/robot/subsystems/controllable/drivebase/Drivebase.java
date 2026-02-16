package frc.robot.subsystems.controllable.drivebase;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.bofalib.control.Controllable;
import frc.robot.subsystems.vision.VisionMeasurementsSupplier;

/**
 * Interface keeping track of all of the robot's driving functions and parts.
 */
public interface Drivebase extends 
    IndirectDrivebase, 
    MiscDrivebase,
    Controllable<DriveControl>
{
    /**
     * @param xInput the x drive input
     * @param yInput the y drive input
     * @param rotInput the rotational drive input
     * @return a drive control strategy applying the x, y, and rotational drive inputs to the robot
     */
    DriveControl getInputDriveControl(
        DoubleSupplier xInput, 
        DoubleSupplier yInput, 
        DoubleSupplier rotInput
    );

    /**
     * @param angleTargetter the targeting strategy
     * @return a drive control strategy that rotates the robot according to the angle targeting strategy
     */
    DriveControl getAngularDriveControl(AngleTargetter angleTargetter);

    /**
     * @param targetRotation the target rotation
     * @return an angle targeting strategy for achieving the target rotation
     */
    AngleTargetter getRotationAngleTargetter(Rotation2d targetRotation);

    /**
     * @param targetLocation the target location
     * @return an angle targeting strategy for looking at the target location
     */
    AngleTargetter getLocationAngleTargetter(Translation2d targetLocation);

    /**
     * Sets the motor mode to brake
     * 
     * @param brake is the brake on or off
     */
    void setBrake(boolean brake);

    static Drivebase create(
        VisionMeasurementsSupplier vision,
        Supplier<Alliance> allianceSupplier
    ) {
        return new SwerveDrivebase(vision, allianceSupplier);
    }
}
