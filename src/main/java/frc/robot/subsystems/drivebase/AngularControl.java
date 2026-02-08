package frc.robot.subsystems.drivebase;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;

public interface AngularControl {
    AngularVelocity calculateRotationRate(Angle targetOrientation);

    void resetAngularControl();
}
