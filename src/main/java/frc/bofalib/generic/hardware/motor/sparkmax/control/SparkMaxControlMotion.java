package frc.bofalib.generic.hardware.motor.sparkmax.control;

import java.util.function.Consumer;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;

public record SparkMaxControlMotion(MotorMotion motion) implements SparkMaxControl {
    @Override
    public String getLoggableName() {
        return "Motion Spark Max Control";
    }

    @Override
    public String getLoggableInfo() {
        // TODO Auto-generated method stub
        return SparkMaxControl.super.getLoggableInfo();
    }

    @Override
    public void visit(Consumer<MotorSetting> settingConsumer,
            Consumer<MotorMotion> motionConsumer) {
        motionConsumer.accept(motion);
    }
}
