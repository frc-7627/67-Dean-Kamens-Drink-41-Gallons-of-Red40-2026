package frc.bofalib.generic.hardware.motor.sparkmax.gains;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.bofalib.gains.GainSelection;
import frc.bofalib.gains.Gains;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxConfigurator;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXCommonConfigurator;

public final class SparkMaxGains implements Gains {
    private final SparkMaxConfigurator configurator;

    public SparkMaxGains(
        SparkMaxConfigurator configurator
    ) {
        this.configurator = configurator;
    }

    @Override
    public void setGain(GainSelection gain, double value) {
        final SparkMaxConfig configs = new SparkMaxConfig();

        switch (gain) {
            case PROPORTIONAL -> {}
            case ACCELERATION -> configs.closedLoop.feedForward.kA(value);
            case DERIVATIVE -> {}
            case GRAVITY -> configs.closedLoop.feedForward.kG(value);
            case INTEGRAL -> {}
            case STATIC -> configs.closedLoop.feedForward.kS(value);
            case VELOCITY -> configs.closedLoop.feedForward.kV(value);
            default -> throw new IllegalArgumentException("Unexpected value: " + gain);
        }

        configurator.apply(configs, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
}
