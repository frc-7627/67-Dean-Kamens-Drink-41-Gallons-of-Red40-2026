package frc.robot.teleop.controller;

import frc.robot.subsystems.drivebase.SwerveSubsystem;

public interface DriverController extends TeleopController {
    TeleopDriveInputs getTeleopDriveInputs(SwerveSubsystem drivebase);
}
