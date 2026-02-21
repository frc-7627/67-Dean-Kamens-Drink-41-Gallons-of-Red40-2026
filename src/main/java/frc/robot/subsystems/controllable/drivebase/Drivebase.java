package frc.robot.subsystems.controllable.drivebase;

import java.util.Optional;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.bofalib.control.Controllable;
import frc.robot.setup.teleop.DriverController;
import frc.robot.subsystems.shared.vision.VisionMeasurementsSupplier;

/**
 * Interface keeping track of all of the robot's driving functions and parts.
 */
public interface Drivebase extends 
    IndirectDrivebase, 
    MiscDrivebase,
    Controllable<DriveControl>,
    DriverController.DriverInputsFunction
{
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
        Optional<VisionMeasurementsSupplier> visionOptional,
        Supplier<Alliance> allianceSupplier
    ) {
        return new SwerveDrivebase(visionOptional, allianceSupplier);
    }
}
