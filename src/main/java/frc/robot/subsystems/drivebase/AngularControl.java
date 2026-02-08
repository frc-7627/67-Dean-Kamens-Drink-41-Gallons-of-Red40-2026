package frc.robot.subsystems.drivebase;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import java.util.function.DoubleSupplier;
import java.util.logging.Logger;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.MutAngularVelocity;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;

public final class AngularControl {
    private static final Logger LOGGER = Logger.getLogger(AngularControl.class.getName());

    private final DoubleSupplier pSupplier;
    private final DoubleSupplier iSupplier;
    private final DoubleSupplier dSupplier;
    private final PIDController controller;
    private final KinematicSupplier kinematicSupplier;
    private final MutAngularVelocity workingAngularVelocity = new MutAngularVelocity(
        0.0, 
        0.0, 
        RadiansPerSecond
    );

    AngularControl(KeyBuilder keyBuilder, KinematicSupplier kinematicSupplier) {
        this.controller = new PIDController(0, 0, 0);
        this.kinematicSupplier = kinematicSupplier;

        keyBuilder.extend("Angular Control");

        this.pSupplier = DashboardItems.createDoublePuller(
            keyBuilder.copyExtendedToString("P"), 
            3.5
        );
        this.iSupplier = DashboardItems.createDoublePuller(
            keyBuilder.copyExtendedToString("I"), 
            0.0
        );
        this.dSupplier = DashboardItems.createDoublePuller(
            keyBuilder.copyExtendedToString("D"), 
            0.0
        );

        controller.enableContinuousInput(-Math.PI, Math.PI);
    }

    void periodic() {
        controller.setPID(
            pSupplier.getAsDouble(), 
            iSupplier.getAsDouble(),
            dSupplier.getAsDouble()
        );
    }

    private Rotation2d getCurrentOrientation() {
        return kinematicSupplier.getPose().getRotation();
    }

    private AngularVelocity getCurrentRotationRate() {
        return RadiansPerSecond.of(kinematicSupplier.getSpeeds().omegaRadiansPerSecond);
    }
    
    public AngularVelocity getRotationRate(Angle targetOrientation) {
        return workingAngularVelocity.mut_replace(
            controller.calculate(
                getCurrentOrientation().getRadians(),
                targetOrientation.in(Radians)
            ), RadiansPerSecond);
    }

    public void reset() {
        controller.reset();
    }

    public void logData(  
        Angle targetOrientationAngle,
        AngularVelocity setRotationRate
    ) {
        final Angle currentOrientationAngle = getCurrentOrientation().getMeasure();
        final AngularVelocity currentRotationRate = getCurrentRotationRate();
        LOGGER.finest("Current orientation angle: " + currentOrientationAngle.in(Degrees) + " deg");
        LOGGER.finest("Target orientation angle: " + targetOrientationAngle.in(Degrees) + " deg");
        LOGGER.finest("Current rotation rate: " + currentRotationRate.in(DegreesPerSecond) + " deg/sec");
        LOGGER.finest("Set rotation rate: " + setRotationRate.in(DegreesPerSecond) + " deg/sec");
    }
}
