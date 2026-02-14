package frc.bofalib.generic.hardware.motor.sparkmax;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public final class SparkMaxConfigurator {
    private final SparkMax sparkMax;

    SparkMaxConfigurator(SparkMax sparkMax) {
        this.sparkMax = sparkMax;
    }

    public void apply(
        SparkMaxConfig config, 
        ResetMode resetMode, 
        PersistMode persistMode
    ) {
        sparkMax.configure(config, resetMode, persistMode);
    }
}
