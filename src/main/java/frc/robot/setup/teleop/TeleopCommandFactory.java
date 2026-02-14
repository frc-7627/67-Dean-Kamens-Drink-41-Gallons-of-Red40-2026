package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.function.Function;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ControlCommand;
import frc.robot.commands.IndicatingWrapperCommand;
import frc.robot.commands.LoggingWrapperCommand;
import frc.robot.commands.control.ToggleControlState;
import frc.robot.commands.drive.misc.Lock;
import frc.robot.commands.drive.misc.ZeroGyro;
import frc.robot.commands.feeder.FeedIn;
import frc.robot.commands.intake.Eject;
import frc.robot.commands.intake.*;
import frc.robot.commands.launcher.ShootOut;

enum TeleopCommandFactory {
    /**
     * 
     */
    LOCK(context -> new Lock(context.drivebase())),
    /**
     * 
     */
    ZERO_GYRO(context -> new ZeroGyro(context.drivebase())),
    /**
     * 
     */
    LOAD_INTAKE(context -> new IndicatingWrapperCommand(
        new Eject(context.intake()), 
        context.indicator()
    )),
    /**
     * 
     */
    STOW_INTAKE(context -> new FoldIn(context.intake())),
    /**
     * 
     */
    LAUNCH_FUEL(context -> new ShootOut(context.launcher())),
    /**
     * 
     */
    TOGGLE_CONTROL_STATE(context -> new ToggleControlState(context.controlStateToggler())),
    /**
     * 
     */
    ROTATE_CCW_90_DEG(context -> new ControlCommand<>(
        context.drivebase(), 
        context.drivebase().getAngularDriveControl(
            context.drivebase().getRotationAngleTargetter(
                Rotation2d.kCCW_90deg
            )
        )
    )),
    /**
     * 
     */
    ORIENT_TO_HUB(context -> new ControlCommand<>(
        context.drivebase(),
        context.drivebase().getAngularDriveControl(
            context.drivebase().getLocationAngleTargetter(
                context.gameInfoSupplier().getHubPosition()
            )
        )
    )),

    /**
     * 
     */
    DRIVE_WHILE_ORIENTING_TO_HUB(context -> new ControlCommand<>(
        context.drivebase(),
        context.inputDriveControl().withRotationControl(
            context.drivebase().getAngularDriveControl(
                context.drivebase().getLocationAngleTargetter(
                    context.gameInfoSupplier().getHubPosition()
                )
            )
        )
    )),

    FEED_AND_SHOOT(context -> new ShootOut(context.launcher()).alongWith(new FeedIn(context.feeder()))),
    
    SHOOT(context -> new ShootOut(context.launcher())),

    FEED(context -> new FeedIn(context.feeder()))
    ;
    

    private final Function<CommandContext, Command> commandSupplier;

    private TeleopCommandFactory(Function<CommandContext, Command> commandSupplier) {
        this.commandSupplier = commandSupplier;
    }

    Consumer<Consumer<Command>> getBinderConsumer(CommandContext context) {
        return binder -> binder.accept(new LoggingWrapperCommand(commandSupplier.apply(context)));
    }
}
