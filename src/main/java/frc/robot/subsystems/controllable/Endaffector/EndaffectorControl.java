package frc.robot.subsystems.controllable.Endaffector;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import static frc.robot.Constants.CHECK_DUTY_CYCLE;
import static frc.robot.Constants.EndaffectorConstants.*;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.bofalib.control.Controllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.control.UniControllable;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxBuilder;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxWrapper;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.loggable.Loggable;
import frc.bofalib.util.FunctionalUtil;

public enum EndaffectorControl implements UniControl<EndaffectorImpl, SparkMaxControl>, Loggable {

    OUT("Eject Out", impl -> impl.dutyCycleSupplier),
    OUT_MANUAL("Eject Out Manual", impl -> impl.manualDutyCycleSupplier),
    IN("Agitate Toward", impl -> FunctionalUtil.negativeSupplier(
        impl.dutyCycleSupplier
    )),
    IN_MANUAL("In Manual", impl -> FunctionalUtil.negativeSupplier(
        impl.manualDutyCycleSupplier
    ));

    private final String name;
    private final Function<EndaffectorImpl, SparkMaxControl> firstControlFunction;

    EndaffectorControl(String name, Function<EndaffectorImpl, DoubleSupplier> dutyCycleFunction) {
        this.name = name;
        this.firstControlFunction = impl -> impl.motor.getSetDutyCycleControl(
            dutyCycleFunction.apply(impl)
        );
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public SparkMaxControl getFirstControl(EndaffectorImpl endaffectorImpl) {
        return firstControlFunction.apply(endaffectorImpl);
    } 
}
