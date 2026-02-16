package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;

public final class TalonFXControlEmpty implements TalonFXControl {
    private static final TalonFXControlEmpty INSTANCE = new TalonFXControlEmpty();

    public static TalonFXControlEmpty getInstance() {
        return INSTANCE;
    }

    private TalonFXControlEmpty() {}

    @Override
    public void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer
    ) {}
}
