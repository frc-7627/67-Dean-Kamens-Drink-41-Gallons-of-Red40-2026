package frc.robot.subsystems.launcher;

import static frc.robot.Constants.LauncherConstants.*;

import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

final class MotorsConfigurator {
    private final TalonFXConfigurator commanderConfigurator;
    private final TalonFXConfigurator minionConfigurator;

    private final CurrentLimitsConfigs currentLimitsConfigs = DEFAULT_CURRENT_LIMITS_CONFIGS;
    private final OpenLoopRampsConfigs openLoopRampsConfigs = DEFAULT_OPEN_LOOP_RAMPS_CONFIGS;
    private final ClosedLoopRampsConfigs closedLoopRampsConfigs = DEFAULT_CLOSED_LOOP_RAMPS_CONFIGS;

    /**
     * Interface for updating the launcher motors' configurations.
     * 
     * @param commanderConfigurator the configurator for the commander motor.
     * @param minionConfigurator the configurator for the minion motor.
     */
    MotorsConfigurator(TalonFXConfigurator commanderConfigurator, TalonFXConfigurator minionConfigurator) {
        this.commanderConfigurator = commanderConfigurator;
        this.minionConfigurator = minionConfigurator;

        commanderConfigurator.apply(currentLimitsConfigs);
        commanderConfigurator.apply(openLoopRampsConfigs);
        commanderConfigurator.apply(closedLoopRampsConfigs);
        commanderConfigurator.apply(DEFAULT_MOTOR_OUTPUT_CONFIGS);
        commanderConfigurator.apply(AUDIO_CONFIGS);

        minionConfigurator.apply(currentLimitsConfigs);
        minionConfigurator.apply(openLoopRampsConfigs);
        minionConfigurator.apply(closedLoopRampsConfigs);
        minionConfigurator.apply(DEFAULT_MOTOR_OUTPUT_CONFIGS);
        minionConfigurator.apply(AUDIO_CONFIGS);
    }

    /**
     * Update the configuration with the new current limit.
     * 
     * @param currentLimit the new current limit.
     */
    void applyCurrentLimit(double currentLimit) {
        currentLimitsConfigs.withStatorCurrentLimit(currentLimit);

        commanderConfigurator.apply(currentLimitsConfigs);
        minionConfigurator.apply(currentLimitsConfigs);
    }

    /**
     * Update the configuration with the new ramp up period.
     * 
     * @param rampUpPeriod the new ramp up period.
     */
    void applyRampUpPeriod(double rampUpPeriod) {
        openLoopRampsConfigs.withDutyCycleOpenLoopRampPeriod(rampUpPeriod);
        closedLoopRampsConfigs.withDutyCycleClosedLoopRampPeriod(rampUpPeriod);

        commanderConfigurator.apply(openLoopRampsConfigs);
        commanderConfigurator.apply(closedLoopRampsConfigs);

        minionConfigurator.apply(openLoopRampsConfigs);
        minionConfigurator.apply(closedLoopRampsConfigs);
    }

    void apply(double currentLimit, double rampUpPeriod) {
        applyCurrentLimit(currentLimit);
        applyRampUpPeriod(rampUpPeriod);
    }
}
