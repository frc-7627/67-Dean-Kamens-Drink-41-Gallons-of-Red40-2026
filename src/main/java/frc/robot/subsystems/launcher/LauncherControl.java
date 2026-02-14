package frc.robot.subsystems.launcher;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static frc.robot.Constants.LauncherConstants.FLYWHEEL_RADIUS_FEET;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.control.UniControl;
import frc.bofalib.generic.hardware.motor.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.MotorVelocity;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchSetting;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchSong;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlSetting;

public enum LauncherControl implements UniControl<LauncherImpl, TalonFXBatchControl> {
    PLAY_SUS("sus"),
    PLAY_BAD_TO_THE_BONE("Bad To the Bone"),
    PLAY_BLOODY_TEARS("bloodytears"),
    PLAY_BLUE_LOBSTER("BlueLobster"),
    PLAY_HCB("hcb"),
    PLAY_PHOTOGRAPH("photograph"),
    PLAY_RICKROLL("rickroll"),
    PLAY_UNDERGROUND("Underground"),
    PLAY_VSAUSE("vsauce"),
    PLAY_WII_SHOP("Wii Shop"),
    SHOOT(impl -> impl.shootSpeedFPSSupplier, 0);

    private final Function<LauncherImpl, TalonFXBatchControl> firstControlFunction;

    private LauncherControl(String songName) {
        this.firstControlFunction = impl -> new TalonFXBatchSong(
            new RobotSong(songName), 
            0, 
            new int[]{1}
        );
    }

    private LauncherControl(
        Function<LauncherImpl, DoubleSupplier> feetPerSecFunction, 
        int dummy
    ) {
        this.firstControlFunction = impl -> new TalonFXBatchSetting(
            new TalonFXControlSetting(
                new MotorVelocity(
                    () -> RadiansPerSecond.of(
                        feetPerSecFunction.apply(impl).getAsDouble() / FLYWHEEL_RADIUS_FEET
                    )
                )
            )
        );
    }

    private LauncherControl(
        Function<LauncherImpl, DoubleSupplier> dutyCycleFunction,
        boolean dummy
    ) {
        this.firstControlFunction = impl -> new TalonFXBatchSetting(
            new TalonFXControlSetting(
                new MotorDutyCycle(dutyCycleFunction.apply(impl))
            )
        );
    }

    @Override
    public TalonFXBatchControl getFirstControl(LauncherImpl target) {
        return firstControlFunction.apply(target);
    }
}
