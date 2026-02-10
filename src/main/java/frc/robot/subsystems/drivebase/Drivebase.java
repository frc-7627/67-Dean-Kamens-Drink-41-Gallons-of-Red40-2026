package frc.robot.subsystems.drivebase;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.bofalib.subsystem.ControllableSubsystem;
import frc.robot.subsystems.vision.VisionMeasurementsSupplier;

/**
 * Interface keeping track of all of the robot's driving functions and parts.
 */
public interface Drivebase extends 
    IndirectDrivebase, 
    MiscDrivebase,
    ControllableSubsystem<DriveControl>
{
    DriveControl getInputDriveControl(
        DoubleSupplier xInput, 
        DoubleSupplier yInput, 
        DoubleSupplier rotInput
    );

    DriveControl getAngularDriveControl(AngleTargetter angleTargetter);

    AngleTargetter getRotationAngleTargetter(Rotation2d targetRotation);

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
