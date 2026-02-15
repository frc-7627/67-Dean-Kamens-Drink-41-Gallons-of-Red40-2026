package frc.bofalib.generic.hardware.motor.sparkmax;

import java.util.Objects;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;

public final class SparkMaxConfigurator {
    private final SparkMax sparkMax;

    SparkMaxConfigurator(SparkMax sparkMax) {
        this.sparkMax = sparkMax;
    }

    public void apply(
        SparkBaseConfig config, 
        ResetMode resetMode, 
        PersistMode persistMode
    ) {
        sparkMax.configure(
            Objects.requireNonNull(config), 
            Objects.requireNonNull(resetMode), 
            Objects.requireNonNull(persistMode)
        );
    }
}
