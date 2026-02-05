package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.function.Function;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.IndicatingWrapperCommand;
import frc.robot.commands.LoggingWrapperCommand;
import frc.robot.commands.MockCommand;
import frc.robot.commands.control.ToggleControlState;
import frc.robot.commands.drive.direct.DriveAngularOrientingTo;
import frc.robot.commands.drive.direct.DriveAngularRotatingBy;
import frc.robot.commands.drive.direct.DriveCombinedOrientingTo;
import frc.robot.commands.drive.misc.Lock;
import frc.robot.commands.drive.misc.ZeroGyro;
import frc.robot.commands.intake.LoadIntake;

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
        new LoadIntake(context.intake()), 
        context.indicator()
    )),
    /**
     * 
     */
    LAUNCH_FUEL(context -> new MockCommand("LaunchFuel")),
    /**
     * 
     */
    TOGGLE_CONTROL_STATE(context -> new ToggleControlState(context.controlStateToggler())),
    /**
     * 
     */
    ROTATE_CCW_90_DEG(context -> new DriveAngularRotatingBy(
        context.drivebase(),
        Rotation2d.kCCW_90deg
    )),
    /**
     * 
     */
    ORIENT_TO_HUB(context -> new DriveAngularOrientingTo(
        context.drivebase(), 
        context.gameInfoSupplier().getHubPosition()
    )),

    /**
     * 
     */
    DRIVE_WHILE_ORIENTING_TO_HUB(context -> new DriveCombinedOrientingTo(
        context.drivebase(), 
        context.gameInfoSupplier().getHubPosition(), 
        context.input()
    )),
    ;

    private final Function<CommandContext, Command> commandSupplier;

    private TeleopCommandFactory(Function<CommandContext, Command> commandSupplier) {
        this.commandSupplier = commandSupplier;
    }

    Consumer<Consumer<Command>> getBinderConsumer(CommandContext context) {
        return binder -> binder.accept(new LoggingWrapperCommand(commandSupplier.apply(context)));
    }
}
