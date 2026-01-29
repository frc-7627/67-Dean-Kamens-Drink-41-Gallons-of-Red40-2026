package frc.robot.teleop.controller;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.InputSupplier;
import frc.robot.subsystems.legacy.SwerveSubsystem;

public interface DriverController extends TeleopController {
    TeleopDriveInputs getTeleopDriveInputs(SwerveSubsystem drivebase);

    Supplier<ChassisSpeeds> getInput(InputSupplier drivebase);
}
