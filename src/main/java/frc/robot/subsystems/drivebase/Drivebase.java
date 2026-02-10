package frc.robot.subsystems.drivebase;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.vision.VisionMeasurementsSupplier;

/**
 * Interface keeping track of all of the robot's driving functions and parts.
 */
public interface Drivebase extends 
    IndirectDrivebase, 
    MiscDrivebase,
    DirectDrivebase
{
    DriveControl getInputDriveControl(
        DoubleSupplier xInput, 
        DoubleSupplier yInput, 
        DoubleSupplier rotInput
    );

    DriveControl getAngularDriveControl(AngleTargetter angleTargetter);

    AngleTargetter getRotationAngleTargetter(Rotation2d targetRotation);

    AngleTargetter getLocationAngleTargetter(Supplier<Translation2d> targetLocationSupplier);

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
