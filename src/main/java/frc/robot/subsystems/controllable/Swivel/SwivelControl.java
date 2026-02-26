package frc.robot.subsystems.controllable.swivel;

import frc.bofalib.generic.hardware.motor.setting.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;
import frc.bofalib.loggable.Loggable;
import frc.bofalib.util.FunctionalUtil;

import java.util.function.DoubleSupplier;
import java.util.function.Function;


import frc.bofalib.generic.control.UniControl;

public enum SwivelControl implements UniControl<
    SwivelImpl,
    SparkMaxControl
>, Loggable{
    FOLD_OUT(
        "Intake Fold Out", impl -> impl.foldDutyCycle),
    FOLD_IN(
        "Intake Fold In", impl -> FunctionalUtil.negativeSupplier(impl.foldDutyCycle));

    private final String name;
    private final Function<SwivelImpl, SparkMaxControl> firstControlFunction;

    SwivelControl(String name, Function<SwivelImpl, DoubleSupplier> magicMotionFunction) {
        this.name = name;
        this.firstControlFunction = impl -> new SparkMaxControlSetting(
            new MotorDutyCycle(magicMotionFunction.apply(impl))
        );
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public SparkMaxControl getFirstControl(SwivelImpl SwivelImpl) {
        return firstControlFunction.apply(SwivelImpl);
    }






















}

