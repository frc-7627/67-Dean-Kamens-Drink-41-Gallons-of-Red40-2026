package frc.robot.teleop.controller;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.SwerveSubsystem;
import swervelib.SwerveInputStream;

public record TeleopDriveInputs(SwerveInputStream driveAngularVelocity,
        SwerveInputStream driveDirectAngle, SwerveInputStream driveRobotOriented,
        SwerveInputStream driveAngularVelocityKeyboard,
        SwerveInputStream driveDirectAngleKeyboard) {
    public Supplier<ChassisSpeeds> getDefaultInputStream(SwerveSubsystem drivebase, ControlContext context) {
        if (context.isSimulation()) {
            return driveDirectAngleKeyboard;
        } else {
            return driveAngularVelocity;
        }
    }
}
