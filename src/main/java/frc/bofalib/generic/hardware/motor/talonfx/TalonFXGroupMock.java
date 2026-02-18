package frc.bofalib.generic.hardware.motor.talonfx;

import java.util.function.DoubleSupplier;
import com.ctre.phoenix6.Orchestra;
import frc.bofalib.generic.control.BoxControllable;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXGroupQuery;
import frc.bofalib.generic.loggable.MockLoggableBase;

final class TalonFXGroupMock extends MockLoggableBase implements 
    TalonFXGroup,
    BoxControllable<TalonFXBatchControl> 
{
    private final ControlBox<TalonFXBatchControl> controlBox = new ControlBox<>();

    TalonFXGroupMock(String name) {
        super(name);
    }

    @Override
    public ControlBox<TalonFXBatchControl> getControlBox() {
        return controlBox;
    }

    @Override
    public TalonFXBatchControl getSetControl(MotorSetting motorSetting) {
        // TODO Auto-generated method stub
        return new TalonFXBatchSetting(new TalonFXControlSetting(motorSetting));
    }

    @Override
    public void beginControl(TalonFXBatchControl control) {
        BoxControllable.super.beginControl(control);
        // TODO Auto-generated method stub
    }

    @Override
    public void runControlInner(TalonFXBatchControl control) {
        // TODO Auto-generated method stub
    }

    @Override
    public TalonFXCommonConfigurator getConfigurator() {
        // TODO Auto-generated method stub
        return new TalonFXMockConfigurator(getLoggableName());
    }

    @Override
    public DoubleSupplier queryDouble(TalonFXGroupQuery query) {
        // TODO Auto-generated method stub
        return () -> 0.0;
    }

    @Override
    public void addToOrchestra(Orchestra orchestra) {
        // TODO Auto-generated method stub
    }
    
}
