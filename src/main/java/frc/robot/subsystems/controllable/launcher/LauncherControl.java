package frc.robot.subsystems.controllable.launcher;

import java.util.Optional;
import java.util.function.DoubleSupplier;
import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.loggable.Loggable;

public sealed interface LauncherControl extends 
    UniControl<LauncherImpl, TalonFXBatchControl>, 
    Loggable
permits LauncherControlVarShoot, LauncherControlSimple {
    Optional<DoubleSupplier> getTargetRPSSupplier(LauncherImpl impl);
}
