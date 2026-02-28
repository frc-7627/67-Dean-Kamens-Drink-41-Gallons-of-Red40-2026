package frc.bofalib.generic.hardware.motor.talonfx.control;

import java.util.function.Consumer;
import com.ctre.phoenix6.controls.ControlRequest;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.loggable.Loggable;

public sealed interface TalonFXControl extends Loggable permits 
    TalonFXControlRequest, 
    TalonFXControlSetting,
    TalonFXControlMotion,
    TalonFXControlEmpty
{
    void visit(
        Consumer<ControlRequest> requestConsumer,
        Consumer<MotorSetting> settingConsumer,
        Consumer<MotorMotion> motionConsumer
    );
}
