package frc.robot.commands.drive.direct;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.subsystems.drivebase.AngularControl;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;
import frc.robot.util.SpeedsCombiner;

abstract class DriveSemidirect extends DriveDirect {
    private final AngularControl angularControl;
    private final SpeedsCombiner speedsCombiner;
    private final OrientationTarget target;

    protected DriveSemidirect(
        SemidirectDrivebase drivebase, 
        OrientationTarget target, 
        Supplier<ChassisSpeeds> input
    ) {
        super(drivebase);

        this.angularControl = drivebase.getAngularControl();
        this.speedsCombiner = new SpeedsCombiner(input);
        this.target = target;
    }

    protected DriveSemidirect(SemidirectDrivebase drivebase, OrientationTarget target) {
        this(drivebase, target, getZeroInput());
    }

    private static Supplier<ChassisSpeeds> getZeroInput() {
        final ChassisSpeeds speeds = new ChassisSpeeds();
        return () -> speeds;
    }

    private Angle getTargetOrientationAngle() {
        return target.getOrientationAngle();
    }

    protected final boolean hasConverged() {
        return angularControl.hasConverged(getTargetOrientationAngle());
    }

    @Override
    protected final ChassisSpeeds getSpeeds() {
        final AngularVelocity rotationRate = angularControl.getRotationRate(
            getTargetOrientationAngle()
        );

        angularControl.logData(getTargetOrientationAngle(), rotationRate);

        return speedsCombiner.getCombinedSpeeds(rotationRate);
    }

    @Override
    public final void initialize() {
        angularControl.reset();
    }
}
