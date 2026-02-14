package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;
import com.ctre.phoenix6.Orchestra;

public final record TalonFXControlTrack(
    Orchestra orchestra,
    int trackNumber
) implements TalonFXControl {
    @Override
    public void visit(
        Consumer<TalonFXControlRequest> requestConsumer, 
        Consumer<TalonFXControlTrack> trackConsumer
    ) {
        trackConsumer.accept(this);
    }
}
