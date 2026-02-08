package frc.robot.commands.drive.direct;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.subsystems.drivebase.AngularControl;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

abstract class DriveSemidirect extends DriveDirect {
    private final AngularControl angularControl;
    private final Supplier<ChassisSpeeds> input;
    private final OrientationTarget target;
    private final ChassisSpeeds workingSpeeds = new ChassisSpeeds();

    protected DriveSemidirect(
        SemidirectDrivebase drivebase, 
        OrientationTarget target, 
        Supplier<ChassisSpeeds> input
    ) {
        super(drivebase);

        this.angularControl = drivebase;
        this.input = input;
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

    @Override
    protected final ChassisSpeeds getSpeeds() {
        final Angle targetOrientationAngle = getTargetOrientationAngle();
        final AngularVelocity rotationRate = angularControl.calculateRotationRate(
            targetOrientationAngle
        );

        final ChassisSpeeds inputSpeeds = input.get();

        workingSpeeds.vxMetersPerSecond = inputSpeeds.vxMetersPerSecond;
        workingSpeeds.vyMetersPerSecond = inputSpeeds.vyMetersPerSecond;
        workingSpeeds.omegaRadiansPerSecond = rotationRate.in(RadiansPerSecond);

        return workingSpeeds;
    }

    @Override
    public final void initialize() {
        angularControl.resetAngularControl();
        target.reset();
    }
}
