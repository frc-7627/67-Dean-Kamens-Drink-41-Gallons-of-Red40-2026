package frc.robot.subsystems.feeder;

import static frc.robot.Constants.FeederConstants.*;
import java.util.function.DoubleSupplier;
import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;
import static frc.robot.Constants.MOTOR_CONFIGURE_FREQUENCY;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.Throttler;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import static frc.robot.Constants.CanIDs.*;

// Colloquially known as The Berlin Wall.
final class FeederImpl extends SubsystemBase implements Feeder {

    // 1 kraken
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(Feeder.class.getSimpleName());
    private final TalonFX feederMotor = new TalonFX(FEEDER_CAN_ID);

    private final CurrentLimitsConfigs currentLimitsConfigs = DEFAULT_CURRENT_LIMITS_CONFIGS;
    private final MotorOutputConfigs motorOutputConfigs = DEFAULT_MOTOR_OUTPUT_CONFIGS;
    
    private final DoubleSupplier currentLimitSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Current Limit"), 
        DEFAULT_CURRENT_LIMIT
    );

    private final DoubleSupplier feedSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Feed Speed"), 
        DEFAULT_FEED_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    private final DoubleSupplier manFeedSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Manual Feed Speed"),
        MANUAL_FEED_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    private final Throttler throttler = new Throttler(MOTOR_CONFIGURE_FREQUENCY);

    @Override
    public void periodic() {
        throttler.execute(() -> {
            apply(
                currentLimitSupplier.getAsDouble(), 
                feedSpeedSupplier.getAsDouble()
            );
        });
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

    @Override 
    public void manualFeedOut(){
        feederMotor.set(-manFeedSpeedSupplier.getAsDouble());
    }

    @Override
    public void manualFeedIn(){
        feederMotor.set(manFeedSpeedSupplier.getAsDouble());
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
