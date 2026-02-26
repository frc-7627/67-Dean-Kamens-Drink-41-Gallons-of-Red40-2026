package frc.bofalib.generic.hardware.motor.sparkmax.control;

import java.util.function.Consumer;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.loggable.Loggable;

public interface SparkMaxControl extends Loggable {
    void visit(
        Consumer<MotorSetting> settingConsumer,
        Consumer<MotorMotion> motionConsumer
    );
}
