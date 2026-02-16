package frc.robot.subsystems.controllable.drivebase;

import java.util.List;
import java.util.function.DoubleSupplier;
import edu.wpi.first.math.controller.PIDController;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.BofaUtil;

final class RotationRateCalculator {
    private final PIDController controller;
    private final DoubleSupplier orientationRadiansSupplier;

    /**
     * Create a rotation rate calculator, with the dashboard key builder and the robot
     * orientation(in radians) supplier.
     * 
     * @param keyBuilder the dashboard key builder
     * @param orientationRadiansSupplier the robot orientation(in radians) supplier
     */
    RotationRateCalculator(KeyBuilder keyBuilder, DoubleSupplier orientationRadiansSupplier) {
        this.controller = new PIDController(0, 0, 0);
        this.orientationRadiansSupplier = orientationRadiansSupplier;

        keyBuilder.extend("Rotation Rate Calculator");

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
    
    /**
     * @param targetRadians the target robot orientation(in radians)
     * @return a rotation rate(in radians per second) to achieve the target orientation(in radians)
     */
    double calculateRadiansPerSecond(double targetRadians) {
        return controller.calculate(
            orientationRadiansSupplier.getAsDouble(), 
            targetRadians
        );
    }

    /**
     * 
     */
    void reset() {
        controller.reset();
    }
}
