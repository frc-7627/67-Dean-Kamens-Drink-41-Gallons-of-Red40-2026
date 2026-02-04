package frc.robot.commands.drive.auto.angular;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;

class DriveAngularOrienting extends Command {
    private final SemidirectDrivebase drivebase;
    private final Supplier<Rotation2d> targetOrientationSupplier;
    private final ChassisSpeeds speeds = new ChassisSpeeds();

    DriveAngularOrienting(
        SemidirectDrivebase drivebase,
        Supplier<Rotation2d> targetOrientationSupplier
    ) {
        this.drivebase = drivebase;
        this.targetOrientationSupplier = targetOrientationSupplier;
    }

    @Override
    public void initialize() {
        drivebase.getAngularControl().reset();
    }

    @Override
    public void execute() {
        final Angle targetOrientationAngle = targetOrientationSupplier.get().getMeasure();
        final AngularVelocity setRotationRate = drivebase.getAngularControl()
            .getRotationRate(targetOrientationAngle);
        speeds.omegaRadiansPerSecond = setRotationRate
            .in(RadiansPerSecond);
        drivebase.driveWithSpeeds(speeds);
        drivebase.getAngularControl().logData(targetOrientationAngle, setRotationRate);
    }

    @Override
    public boolean isFinished() {
        return drivebase
            .getAngularControl()
            .hasConverged(targetOrientationSupplier.get().getMeasure());
    }
}
