package frc.bofalib.generic.hardware.motor.talonfx.control;

import java.util.function.Consumer;
import com.ctre.phoenix6.controls.ControlRequest;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;

public final class TalonFXControlEmpty implements TalonFXControl {
    private static final TalonFXControlEmpty INSTANCE = new TalonFXControlEmpty();

    public static TalonFXControlEmpty getInstance() {
        return INSTANCE;
    }

    private TalonFXControlEmpty() {}

    @Override
    public String getLoggableName() {
        return "Empty Talon FX Control";
    }

    @Override
    public void visit(
        Consumer<ControlRequest> requestConsumer,
        Consumer<MotorSetting> settingConsumer 
        //Consumer<MotorMotion> motionConsumer
    ) {}
}
