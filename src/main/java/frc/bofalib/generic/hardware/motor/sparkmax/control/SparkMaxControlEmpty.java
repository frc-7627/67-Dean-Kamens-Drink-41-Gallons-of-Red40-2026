package frc.bofalib.generic.hardware.motor.sparkmax.control;

import java.util.function.Consumer;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;

public final class SparkMaxControlEmpty implements SparkMaxControl {
    private static final SparkMaxControlEmpty INSTANCE = new SparkMaxControlEmpty();

    public static SparkMaxControlEmpty getInstance() {
        return INSTANCE;
    }

    private SparkMaxControlEmpty() {}

    @Override
    public String getLoggableName() {
        return "Empty Spark Max Control";
    }

    @Override
    public void visit(
        Consumer<MotorSetting> settingConsumer,
        Consumer<MotorMotion> motionConsumer
    ) {}
}
