package frc.robot.subsystems.feeder;

import static frc.robot.Constants.FeederConstants.*;
import java.util.function.DoubleSupplier;
import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;
import static frc.robot.Constants.MOTOR_CONFIGURE_FREQUENCY;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.Util;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;

// Colloquially known as The Berlin Wall.
final class FeederImpl extends SubsystemBase implements Feeder {

    // 1 kraken
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(Feeder.class.getSimpleName());
    private final TalonFX feederMotor = new TalonFX(0);

    private final CurrentLimitsConfigs currentLimitsConfigs = DEFAULT_CURRENT_LIMITS_CONFIGS;
    private final MotorOutputConfigs motorOutputConfigs = DEFAULT_MOTOR_OUTPUT_CONFIGS;
    
    private final DoubleSupplier currentLimitSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Current Limit"), 
        DEFAULT_CURRENT_LIMIT
    );

    private final DoubleSupplier feedSpeedSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Feed Speed"), 
        DEFAULT_FEED_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    @Override
    public void periodic() {
        Util.throttle(() -> {
            apply(
                currentLimitSupplier.getAsDouble(), 
                feedSpeedSupplier.getAsDouble()
            );
        }, MOTOR_CONFIGURE_FREQUENCY);
    }

    private void apply(double currentLimit, double feedSpeed) {
        applyCurrentLimit(currentLimit);
        applyFeedSpeed(feedSpeed);
    }

    void applyCurrentLimit(double currentLimit) {
        currentLimitsConfigs.withStatorCurrentLimit(currentLimit);
    }

    void applyFeedSpeed(double feedSpeed) {
        motorOutputConfigs.withPeakForwardDutyCycle(feedSpeed);
        motorOutputConfigs.withPeakReverseDutyCycle(-feedSpeed);
    }

    @Override
    public void feedIn() {
        feederMotor.set(feedSpeedSupplier.getAsDouble());
    }

    @Override
    public void feedOut() {
        feederMotor.set(-feedSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * 
     * Stops the motor.
     */
    @Override
    public void stop() {
        feederMotor.stopMotor();
    }

}
