package frc.robot.subsystems.controllable.drivebase;

import java.util.Optional;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.bofalib.control.Controllable;
import frc.robot.setup.teleop.JoystickInputs;
import frc.robot.subsystems.shared.gameinfo.GeneralGameInfoSupplier;
import frc.robot.subsystems.shared.vision.VisionMeasurementsSupplier;

/**
 * Interface keeping track of all of the robot's driving functions and parts.
 */
public interface Drivebase extends
        IndirectDrivebase,
        MiscDrivebase,
        Controllable<DriveControl> {
    /**
     * @param xInput   the x drive input
     * @param yInput   the y drive input
     * @param rotInput the rotational drive input
     * @return a drive control strategy applying the x, y, and rotational drive
     *         inputs to the robot
     */
    DriveControl getInputDriveControl(
            JoystickInputs inputs);

    /**
     * @param angleTargetter the targeting strategy
     * @return a drive control strategy that rotates the robot according to the
     *         angle targeting strategy
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

    AngleTargetter getLocationSupplierAngleTargetter(Supplier<Translation2d> targetLocationSupplier);

    /**
     * @return a distance targeting strategy for measuring the distance to hub
     *         depending on alliance
     */
    DistanceTargetter getDistanceTargetterToHub();

    DistanceTargetter getDistanceTargetterToAllianceZone(Side side);

    /**
     * Sets the motor mode to brake
     * 
     * @param brake is the brake on or off
     */
    void setBrake(boolean brake);

    Zone getZone();

    static Drivebase create(
            Optional<VisionMeasurementsSupplier> visionOptional,
            GeneralGameInfoSupplier gameInfoSupplier) {
        return new SwerveDrivebase(visionOptional, gameInfoSupplier);
    }
}
