package frc.bofalib.generic.hardware.motor.talonfx.control;

import java.util.function.Consumer;

import com.ctre.phoenix6.controls.ControlRequest;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;

public final record TalonFXControlRequest(
    ControlRequest request
) implements TalonFXControl {
    @Override
    public String getLoggableName() {
        return "Request Talon FX Control";
    }

    @Override
    public String getLoggableInfo() {
        // TODO Auto-generated method stub
        return TalonFXControl.super.getLoggableInfo();
    }

    @Override
    public void visit(
        Consumer<ControlRequest> requestConsumer,
        Consumer<MotorSetting> settingConsumer, 
        Consumer<MotorMotion> motionConsumer
    ) {
        requestConsumer.accept(request);    
        System.out.println("Visting talon control requests");
    }
}
