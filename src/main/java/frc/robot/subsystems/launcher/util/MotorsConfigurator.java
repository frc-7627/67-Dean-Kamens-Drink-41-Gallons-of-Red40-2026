package frc.robot.subsystems.launcher.util;

import static frc.robot.Constants.LauncherConstants.*;

import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

public class MotorsConfigurator {
    private final TalonFXConfigurator commanderConfigurator;
    private final TalonFXConfigurator minionConfigurator;

    private final CurrentLimitsConfigs currentLimitsConfigs = DEFAULT_CURRENT_LIMITS_CONFIGS;
    private final MotorOutputConfigs motorOutputConfigs = DEFAULT_MOTOR_OUTPUT_CONFIGS;
    private final OpenLoopRampsConfigs openLoopRampsConfigs = DEFAULT_OPEN_LOOP_RAMPS_CONFIGS;
    private final ClosedLoopRampsConfigs closedLoopRampsConfigs = DEFAULT_CLOSED_LOOP_RAMPS_CONFIGS;

    public MotorsConfigurator(TalonFXConfigurator commanderConfigurator, TalonFXConfigurator minionConfigurator) {
        this.commanderConfigurator = commanderConfigurator;
        this.minionConfigurator = minionConfigurator;

        commanderConfigurator.apply(currentLimitsConfigs);
        commanderConfigurator.apply(motorOutputConfigs);
        commanderConfigurator.apply(openLoopRampsConfigs);
        commanderConfigurator.apply(closedLoopRampsConfigs);
        commanderConfigurator.apply(AUDIO_CONFIGS);

        minionConfigurator.apply(currentLimitsConfigs);
        minionConfigurator.apply(motorOutputConfigs);
        minionConfigurator.apply(openLoopRampsConfigs);
        minionConfigurator.apply(closedLoopRampsConfigs);
        minionConfigurator.apply(AUDIO_CONFIGS);
    }

    public void applyCurrentLimit(double currentLimit) {
        currentLimitsConfigs.withStatorCurrentLimit(currentLimit);

        commanderConfigurator.apply(currentLimitsConfigs);
        minionConfigurator.apply(currentLimitsConfigs);
    }

    public void applyShootSpeed(double shootSpeed) {
        motorOutputConfigs.withPeakForwardDutyCycle(shootSpeed);
        motorOutputConfigs.withPeakReverseDutyCycle(-shootSpeed);

        commanderConfigurator.apply(motorOutputConfigs);
        minionConfigurator.apply(motorOutputConfigs);
    }

    public void applyRampUpPeriod(double rampUpPeriod) {
        openLoopRampsConfigs.withDutyCycleOpenLoopRampPeriod(rampUpPeriod);
        closedLoopRampsConfigs.withDutyCycleClosedLoopRampPeriod(rampUpPeriod);

        commanderConfigurator.apply(openLoopRampsConfigs);
        commanderConfigurator.apply(closedLoopRampsConfigs);

        minionConfigurator.apply(openLoopRampsConfigs);
        minionConfigurator.apply(closedLoopRampsConfigs);
    }
}
