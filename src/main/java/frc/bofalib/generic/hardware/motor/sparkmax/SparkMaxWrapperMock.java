package frc.bofalib.generic.hardware.motor.sparkmax;

import java.util.Objects;
import frc.bofalib.generic.control.BoxControllableDefaultable;
import frc.bofalib.generic.control.DefaultableControlBox;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlEmpty;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlMotion;
import frc.bofalib.generic.hardware.motor.sparkmax.query.SparkMaxQuery;
import frc.bofalib.generic.loggable.MockLoggableBase;

final class SparkMaxWrapperMock extends
    MockLoggableBase
implements 
    SparkMaxWrapper,
    BoxControllableDefaultable<SparkMaxControl>
{
    private final DefaultableControlBox<SparkMaxControl> controlBox = new DefaultableControlBox<>(
        SparkMaxControlEmpty.getInstance()
    );

    SparkMaxWrapperMock(String name) {
        super(name);
    }

    @Override
    public DefaultableControlBox<SparkMaxControl> getControlBox() {
        return controlBox;
    }

    @Override
    public void beginControlInner(SparkMaxControl control) {
        // TODO Auto-generated method stub
    }

    @Override
    public void runControlInner(SparkMaxControl control) {
        // TODO Auto-generated method stub
    }

    @Override
    public void endControlInner(SparkMaxControl control) {
        // TODO Auto-generated method stub
    }

    @Override
    public double queryDouble(SparkMaxQuery query) {
        // TODO Auto-generated method stub
        return 0.0;
    }

    @Override
    public SparkMaxConfigurator getConfigurator() {
        return new SparkMaxConfiguratorMock(getLoggableName());
    }

    @Override
    public SparkMaxControl getSetControl(MotorSetting motorSetting) {
        // TODO Auto-generated method stub
        return SparkMaxControlEmpty.getInstance();
    }
    
    @Override
    public SparkMaxControl getMotionControl(MotorMotion motorMotion) {
        return new SparkMaxControlMotion(
            Objects.requireNonNull(motorMotion)
        );
    }
}
