package frc.bofalib.generic.hardware.motor.sparkmax;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;


public interface SparkMaxConfigurator {
    void apply(
        SparkBaseConfig config, 
        ResetMode resetMode, 
        PersistMode persistMode
    );
}
