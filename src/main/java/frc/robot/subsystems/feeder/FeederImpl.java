package frc.robot.subsystems.feeder;

import static frc.robot.Constants.FeederConstants.*;
import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.dashboard.fields.PullingDouble;
import static frc.robot.Constants.CanIDs.*;

// Colloquially known as The Berlin Wall.
class FeederImpl extends SubsystemBase implements Feeder {

    // 1 kraken

    private static final String DASHBOARD_NAME = Feeder.class.getName();
    private final TalonFX feederMotor = new TalonFX(FEEDER_CAN_ID);

    private final CurrentLimitsConfigs currentLimitsConfigs = DEFAULT_CURRENT_LIMITS_CONFIGS;
    private final MotorOutputConfigs motorOutputConfigs = DEFAULT_MOTOR_OUTPUT_CONFIGS;

    private final PullingDouble currentLimit = new PullingDouble(
            DASHBOARD_NAME,
            "Current Limit",
            this::applyCurrentLimit,
            DEFAULT_CURRENT_LIMIT);

    private final PullingDouble feedSpeed = new PullingDouble(
            DASHBOARD_NAME,
            "Feed Speed",
            CHECK_SIMPLE_MOTOR_SPEED,
            this::applyFeedSpeed,
            DEFAULT_FEED_SPEED);

    void applyCurrentLimit(double currentLimit) {
        currentLimitsConfigs.withStatorCurrentLimit(currentLimit);
    }

    void applyFeedSpeed(double feedSpeed) {
        motorOutputConfigs.withPeakForwardDutyCycle(feedSpeed);
        motorOutputConfigs.withPeakReverseDutyCycle(-feedSpeed);
    }

    @Override
    public void feedIn() {
        feederMotor.set(feedSpeed.getPulled());
    }

    @Override
    public void feedOut() {
        feederMotor.set(-feedSpeed.getPulled());
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
