package frc.robot.teleop.controller;

import frc.robot.subsystems.legacy.SwerveSubsystem;

public interface DriverController extends TeleopController {
    TeleopDriveInputs getTeleopDriveInputs(SwerveSubsystem drivebase);
}
