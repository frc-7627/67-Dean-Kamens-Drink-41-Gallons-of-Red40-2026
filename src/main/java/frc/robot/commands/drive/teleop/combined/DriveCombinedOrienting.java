package frc.robot.commands.drive.teleop.combined;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.SemidirectDrivebase;
import frc.robot.util.SpeedsCombiner;

class DriveCombinedOrienting extends Command {
    private final SemidirectDrivebase drivebase;
    private final SpeedsCombiner speedsCombiner;
    private final Supplier<Rotation2d> targetOrientationSupplier;

    DriveCombinedOrienting(
        SemidirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> translationInput,
        Supplier<Rotation2d> targetOrientationSupplier
    ) {
        this.drivebase = drivebase;
        this.speedsCombiner = new SpeedsCombiner(translationInput);
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
        
        drivebase.driveWithSpeeds(
            speedsCombiner.getCombinedSpeeds(
                setRotationRate
            )
        );

        drivebase.getAngularControl().logData(targetOrientationAngle, setRotationRate);
    }
}
