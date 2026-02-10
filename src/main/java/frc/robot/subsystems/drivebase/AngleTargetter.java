package frc.robot.subsystems.drivebase;

public interface AngleTargetter {
    default void initialize() {}

    double getTargetRadians();
}
