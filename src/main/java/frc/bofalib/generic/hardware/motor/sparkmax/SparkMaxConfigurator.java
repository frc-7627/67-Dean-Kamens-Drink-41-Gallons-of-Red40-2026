package frc.bofalib.generic.hardware.motor.sparkmax;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import frc.bofalib.generic.hardware.motor.MotorConfigurator;


public interface SparkMaxConfigurator extends MotorConfigurator {
    void apply(
        SparkBaseConfig config, 
        ResetMode resetMode, 
        PersistMode persistMode
    );
}
