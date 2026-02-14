package frc.robot.subsystems.intake;

import static frc.robot.Constants.LauncherConstants.*;

import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

final class MotorsConfigurator {
    private final TalonFXConfigurator KrakenConfigurator;

    private final CurrentLimitsConfigs currentLimitsConfigs = DEFAULT_CURRENT_LIMITS_CONFIGS;
    private final OpenLoopRampsConfigs openLoopRampsConfigs = DEFAULT_OPEN_LOOP_RAMPS_CONFIGS;
    private final ClosedLoopRampsConfigs closedLoopRampsConfigs = DEFAULT_CLOSED_LOOP_RAMPS_CONFIGS;

    /**
     * Interface for updating the launcher motors' configurations.
     * 
     * @param KrakenConfigurator the configurator for the commander motor.
     */
    MotorsConfigurator(TalonFXConfigurator KrakenConfigurator) {
        this.KrakenConfigurator = KrakenConfigurator;

        KrakenConfigurator.apply(currentLimitsConfigs);
        KrakenConfigurator.apply(openLoopRampsConfigs);
        KrakenConfigurator.apply(closedLoopRampsConfigs);
        KrakenConfigurator.apply(DEFAULT_MOTOR_OUTPUT_CONFIGS);
        KrakenConfigurator.apply(AUDIO_CONFIGS);
        KrakenConfigurator.apply(SLOT0_CONFIGS);
    }

    /**
     * Update the configuration with the new current limit.
     * 
     * @param currentLimit the new current limit.
     */
    void applyCurrentLimit(double currentLimit) {
        currentLimitsConfigs.withStatorCurrentLimit(currentLimit);

        KrakenConfigurator.apply(currentLimitsConfigs);
    }

    /**
     * Update the configuration with the new ramp up period.
     * 
     * @param rampUpPeriod the new ramp up period.
     */
    void applyRampUpPeriod(double rampUpPeriod) {
        openLoopRampsConfigs.withDutyCycleOpenLoopRampPeriod(rampUpPeriod);
        closedLoopRampsConfigs.withDutyCycleClosedLoopRampPeriod(rampUpPeriod);

        KrakenConfigurator.apply(openLoopRampsConfigs);
        KrakenConfigurator.apply(closedLoopRampsConfigs);
    }

    void apply(double currentLimit, double rampUpPeriod) {
        applyCurrentLimit(currentLimit);
        applyRampUpPeriod(rampUpPeriod);
    }
}
