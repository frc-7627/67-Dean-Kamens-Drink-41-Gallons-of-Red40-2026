package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.Constants.LauncherConstants.FLYWHEEL_RADIUS_FEET;

import frc.bofalib.control.Controllable;
import frc.bofalib.generic.music.MusicalSubsystem;
import frc.bofalib.query.BooleanQueryable;
import frc.robot.subsystems.controllable.drivebase.DrivebaseKinematics;

public interface Launcher extends 
    MusicalSubsystem, 
    Controllable<LauncherControl>, 
    BooleanQueryable<LauncherBooleanQuery> 
{
    static double toLinearVelocityFPS(double angularVelocityRPS) {
        return RadiansPerSecond.convertFrom(
            angularVelocityRPS, 
            RotationsPerSecond
        ) * FLYWHEEL_RADIUS_FEET;
    }

    static double toAngularVelocityRPS(double linearVelocityFPS) {
        return RotationsPerSecond.convertFrom(
            linearVelocityFPS / FLYWHEEL_RADIUS_FEET, 
            RadiansPerSecond
        );
    }

    static Launcher create(DrivebaseKinematics kinematics) {
        return new LauncherImpl(kinematics);
    }
}
