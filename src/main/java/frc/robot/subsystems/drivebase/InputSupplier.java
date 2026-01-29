package frc.robot.subsystems.drivebase;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface InputSupplier {
    Supplier<ChassisSpeeds> getInput(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rot);
}
