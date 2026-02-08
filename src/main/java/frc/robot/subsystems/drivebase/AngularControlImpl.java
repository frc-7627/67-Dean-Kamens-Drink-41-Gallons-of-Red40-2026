package frc.robot.subsystems.drivebase;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.logging.Logger;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.MutAngularVelocity;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.BofaUtil;

final class AngularControlImpl implements AngularControl {
    private static final Logger LOGGER = Logger.getLogger(AngularControlImpl.class.getName());
    
    private final PIDController controller;
    private final KinematicSupplier kinematicSupplier;
    private final MutAngularVelocity workingAngularVelocity = new MutAngularVelocity(
        0.0, 
        0.0, 
        RadiansPerSecond
    );

    AngularControlImpl(KeyBuilder keyBuilder, KinematicSupplier kinematicSupplier) {
        this.controller = new PIDController(0, 0, 0);
        this.kinematicSupplier = kinematicSupplier;

        keyBuilder.extend("Angular Control");

        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            BofaUtil.compose(
                controller::setP, 
                DashboardItems.createDoublePuller(
                    keyBuilder.copyExtendedToString("P"), 
                    3.5
                )
            ),
            BofaUtil.compose(
                controller::setI,
                DashboardItems.createDoublePuller(
                    keyBuilder.copyExtendedToString("I"), 
                    0.0
                )
            ),
            BofaUtil.compose(
                controller::setD,
                DashboardItems.createDoublePuller(
                    keyBuilder.copyExtendedToString("D"),
                    0.0
                )
            )
        ));

        controller.enableContinuousInput(-Math.PI, Math.PI);
    }

    private Rotation2d getCurrentOrientation() {
        return kinematicSupplier.getPose().getRotation();
    }

    private AngularVelocity getCurrentRotationRate() {
        return RadiansPerSecond.of(kinematicSupplier.getSpeeds().omegaRadiansPerSecond);
    }
    
    @Override
    public AngularVelocity calculateRotationRate(Angle targetOrientationAngle) {
        final Rotation2d currentOrientation = getCurrentOrientation();
        final AngularVelocity currentRotationRate = getCurrentRotationRate();

        final AngularVelocity calculatedRotationRate = workingAngularVelocity.mut_replace(
            controller.calculate(
                currentOrientation.getRadians(),
                targetOrientationAngle.in(Radians)
            ), RadiansPerSecond);

        LOGGER.finest(
            "Current orientation angle: " + 
            currentOrientation.getDegrees() + 
            " deg"
        );
        LOGGER.finest(
            "Target orientation angle: " + 
            targetOrientationAngle.in(Degrees) + 
            " deg"
        );
        LOGGER.finest(
            "Current rotation rate: " + 
            currentRotationRate.in(DegreesPerSecond) + 
            " deg/sec"
        );
        LOGGER.finest(
            "Calculated rotation rate: " + 
            calculatedRotationRate.in(DegreesPerSecond) + 
            " deg/sec"
        );

        return calculatedRotationRate;
    }

    @Override
    public void resetAngularControl() {
        controller.reset();
    }
}
