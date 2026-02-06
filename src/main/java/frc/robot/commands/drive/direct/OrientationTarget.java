package frc.robot.commands.drive.direct;

import edu.wpi.first.units.measure.Angle;

abstract class OrientationTarget {
    abstract Angle getOrientationAngle();

    void reset() {}
}
