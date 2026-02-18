package frc.bofalib.generic.hardware.motor.sparkmax.control;

import frc.bofalib.generic.hardware.motor.setting.MotorSetting;

public record SparkMaxControlSetting(MotorSetting setting) implements SparkMaxControl {
    @Override
    public String getLoggableName() {
        return "Setting Spark Max Control";
    }

    @Override
    public String getLoggableInfo() {
        // TODO Auto-generated method stub
        return SparkMaxControl.super.getLoggableInfo();
    }
}
