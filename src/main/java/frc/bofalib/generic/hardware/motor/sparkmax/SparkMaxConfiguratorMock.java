package frc.bofalib.generic.hardware.motor.sparkmax;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import frc.bofalib.generic.hardware.motor.MotorConfiguratorBase;

final class SparkMaxConfiguratorMock extends MotorConfiguratorBase implements SparkMaxConfigurator {
    SparkMaxConfiguratorMock(String motorName) {
        super(motorName);
    }

    @Override
    public void apply(SparkBaseConfig config, ResetMode resetMode, PersistMode persistMode) {
        // TODO Auto-generated method stub
        
    }
}
