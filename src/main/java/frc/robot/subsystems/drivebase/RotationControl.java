package frc.robot.subsystems.drivebase;

import java.util.function.BiFunction;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

final class RotationControl implements BiFunction<Rotation2d, Double, ChassisSpeeds> {
    private final PIDController controller;

    RotationControl(double kp, double ki, double kd) {
        this.controller = new PIDController(5, 0, 0);
    }

    @Override
    public ChassisSpeeds apply(Rotation2d deltaRot, Double radius) {
        return new ChassisSpeeds(0, 0,
                controller.calculate(deltaRot.getRadians() * radius) / radius);
    }
}
