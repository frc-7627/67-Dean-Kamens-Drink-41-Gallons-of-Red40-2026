package frc.bofalib.generic.hardware.motor.talonfx.control;

import frc.bofalib.loggable.Loggable;

public record TalonFXBatchControl(TalonFXControl control) implements Loggable {
    @Override
    public String getLoggableName() {
        return "Talon FX Batch Control";
    }

    @Override
    public String getLoggableInfo() {
        return getLoggableName() + ": " + control.getLoggableInfo();
    }
}
