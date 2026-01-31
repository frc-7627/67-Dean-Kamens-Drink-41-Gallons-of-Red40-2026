package frc.robot.teleop.command;

import java.util.function.Consumer;
import java.util.function.Function;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.commands.control.ToggleControlState;
import frc.robot.commands.drive.direct.Lock;
import frc.robot.commands.drive.semidirect.DriveDirectlyOrientingTo;
import frc.robot.commands.drive.semidirect.DriveUntilOrientedTo;
import frc.robot.commands.drive.semidirect.DriveUntilRotatedBy;
import frc.robot.commands.drive.teleop.ZeroGyro;
import frc.robot.commands.intake.LoadIntake;

public enum TeleopCommandFactory {
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
    LOAD_INTAKE(context -> new LoadIntake(context.indicator(), context.intake())),
    /**
     * 
     */
    LAUNCH_FUEL(context -> new PrintCommand("launch fuel")),
    /**
     * 
     */
    TOGGLE_CONTROL_STATE(context -> new ToggleControlState(context.controlStateToggler())),
    /**
     * 
     */
    ROTATE_CCW_90_DEG(context -> new DriveUntilRotatedBy(
        context.drivebase(), 
        Rotation2d.kCCW_90deg
    )),
    /**
     * 
     */
    ORIENT_TO_HUB(context -> new DriveUntilOrientedTo(
        context.drivebase(), 
        context.gameInfoSupplier().getHubPosition()
    )),

    /**
     * 
     */
    DRIVE_ORIENTING_TO_HUB(context -> new DriveDirectlyOrientingTo(
        context.drivebase(), 
        context.input(), 
        context.gameInfoSupplier().getHubPosition()
    )),
    ;

    private final Function<CommandContext, Command> commandSupplier;

    private TeleopCommandFactory(Function<CommandContext, Command> commandSupplier) {
        this.commandSupplier = commandSupplier;
    }

    Consumer<Consumer<Command>> getBinderConsumer(CommandContext context) {
        return binder -> binder.accept(commandSupplier.apply(context));
    }
}
