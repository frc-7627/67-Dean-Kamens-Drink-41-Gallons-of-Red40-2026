package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.function.Function;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.bofalib.generic.control.ControlCommand;
import frc.robot.RobotSong;
import frc.robot.commands.IndicatingWrapperCommand;
import frc.robot.commands.LoggingWrapperCommand;
import frc.robot.commands.RobotSongCommand;
import frc.robot.commands.Rocker;
import frc.robot.commands.Score;
import frc.robot.commands.control.ToggleControlState;
import frc.robot.commands.drive.misc.*;
import frc.robot.subsystems.controllable.agitator.AgitatorControl;
import frc.robot.subsystems.controllable.feeder.FeederControl;
import frc.robot.subsystems.controllable.intake.Intake;
import frc.robot.subsystems.controllable.intake.IntakeControl;
import frc.robot.subsystems.controllable.launcher.LauncherControlSimple;
import frc.robot.subsystems.controllable.swivel.Swivel;
import frc.robot.subsystems.controllable.swivel.SwivelControl;

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
    ZERO_GYRO_WITH_ALLIANCE(context -> new ZeroGyroWithAlliance(context.drivebase())),
    /**
     * 
     */
    LOAD_INTAKE(context -> new IndicatingWrapperCommand(
        new ControlCommand<>(context.intake(), IntakeControl.LOAD), 
        context.indicator()
    )),

    /**
     * 
     */
    //COOLER_LOAD(context -> new IndicatingWrapperCommand(new ControlCommand<>(context.intake(), IntakeControl.)) TODO: Put this back when you figure out encoders

    /**
     * 
     */
    EJECT_INTAKE(context -> new IndicatingWrapperCommand(
    new ControlCommand<>(context.intake(), IntakeControl.EJECT), 
    context.indicator()
)),

    /**
     * 
     */
    COOLER_EJECT(context -> new ControlCommand<>(context.intake(), IntakeControl.EJECT)
        .alongWith(new ControlCommand<>(context.agitator(), AgitatorControl.AWAY))),
    /**
     * 
     */
    STOW_INTAKE(context -> new ControlCommand<>(context.swivel(), SwivelControl.FOLD_IN)),
    /**
     * 
     */
    LAUNCH_FUEL(context -> new ControlCommand<>(context.launcher(), LauncherControlSimple.SHOOT_MANUAL)),
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

    FEED_AND_SHOOT(context -> new ControlCommand<>(context.launcher(), LauncherControlSimple.SHOOT_MANUAL)
        .alongWith(new ControlCommand<>(context.feeder(), FeederControl.FEED_IN))),

    AGITATE_FEED_AND_SHOOT(context -> new ControlCommand<>(context.launcher(), LauncherControlSimple.SHOOT_MANUAL)
    .alongWith(new ControlCommand<>(context.feeder(), FeederControl.FEED_IN)
    .alongWith(new ControlCommand<>(context.agitator(), AgitatorControl.TOWARD)))),

    PERFECT_CELL(context -> new ControlCommand<>(context.launcher(), LauncherControlSimple.SHOOT_MANUAL)
    .raceWith(new WaitCommand(1.7627)).andThen(new ControlCommand<>(context.launcher(), LauncherControlSimple.SHOOT_MANUAL)
    .alongWith(new ControlCommand<>(context.feeder(), FeederControl.FEED_IN)
    .alongWith(new ControlCommand<>(context.agitator(), AgitatorControl.TOWARD))))),
    
    SHOOT(context -> new ControlCommand<>(context.launcher(), LauncherControlSimple.SHOOT_MANUAL)),

    ALL_ONE_BUTTON_SHOOT(context -> new Score(context.gameInfoSupplier(), context.indicator(), 
    context.drivebase(), context.launcher(), context.agitator(), context.feeder())
    .alongWith( new ControlCommand<>(context.swivel(), SwivelControl.FOLD_IN))
    .alongWith(new ControlCommand<>(context.intake(), IntakeControl.LOAD))),

    FEED(context -> new ControlCommand<>(context.feeder(), FeederControl.FEED_IN)),
    AGITATE(context -> new ControlCommand<>(context.agitator(), AgitatorControl.TOWARD)),

    REVERSE_AGITATE(context -> new ControlCommand<>(context.agitator(), AgitatorControl.AWAY)),

    SWIVEL_OUT(context -> new ControlCommand<>(context.swivel(), SwivelControl.FOLD_OUT)),

    ROCKER(context -> new Rocker(context.swivel())),

    PLAY_SONG(context -> new RobotSongCommand(context.musicalSubsystems(), RobotSong.getRandomSong())),

    RESET_SWIVEL(context -> Commands.runOnce(context.swivel()::reset, context.swivel()))
    ;
    

    private final Function<CommandContext, Command> commandSupplier;

    private TeleopCommandFactory(Function<CommandContext, Command> commandSupplier) {
        this.commandSupplier = commandSupplier;
    }

    Consumer<Consumer<Command>> getBinderConsumer(CommandContext context) {
        return binder -> binder.accept(new LoggingWrapperCommand(commandSupplier.apply(context)));
    }
}



/*
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨🟨⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜🟨🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨🟨🟨⬜⬜⬜🟨🟨⬜⬜⬜🟨🟨🟨🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨🟨🟨⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜🟨🟨🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜⬜🟨🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟨🟨⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
🟦🟦🟦⬜⬜🟦🟦🟦🟦⬜⬜🟦🟦🟦⬜⬜⬜⬜⬜⬜⬜⬜⬜🟦🟦🟦⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜🟦🟦🟦⬜⬜
⬜🟦🟦⬜⬜🟦🟦🟦🟦⬜⬜🟦🟦🟦🟦🟦🟦🟦🟦🟦🟦⬜⬜🟦🟦🟦⬜🟦🟦🟦🟦🟦🟦🟦⬜🟦🟦🟦🟦⬜⬜🟦🟦🟦🟦🟦🟦⬜⬜🟦🟦🟦🟦🟦🟦🟦🟦🟦🟦🟦
⬜🟦🟦🟦⬜🟦🟦🟦🟦🟦🟦🟦🟦⬜🟦🟦⬜⬜⬜🟦🟦⬜⬜🟦🟦🟦⬜🟦🟦🟦⬜🟦🟦🟦🟦⬜🟦🟦🟦⬜⬜🟦⬜⬜⬜🟦🟦🟦⬜🟦🟦🟦🟦🟦🟦🟦🟦🟦⬜⬜
⬜🟦🟦🟦🟦🟦🟦⬜🟦🟦🟦🟦🟦⬜⬜🟦🟦🟦🟦🟦🟦🟦⬜🟦🟦🟦⬜🟦🟦🟦⬜⬜🟦🟦⬜⬜🟦🟦🟦⬜⬜🟦🟦🟦🟦🟦🟦🟦⬜🟦🟦🟦⬜⬜⬜🟦🟦🟦⬜⬜
⬜⬜🟦🟦🟦🟦⬜⬜🟦🟦🟦🟦⬜⬜🟦🟦🟦⬜⬜🟦🟦🟦⬜🟦🟦🟦⬜🟦🟦🟦⬜⬜🟦🟦⬜⬜🟦🟦🟦⬜🟦🟦🟦⬜⬜🟦🟦🟦⬜🟦🟦🟦⬜⬜⬜🟦🟦🟦🟦⬜
⬜⬜🟦🟦🟦🟦⬜⬜🟦🟦🟦🟦⬜⬜🟦🟦🟦🟦🟦🟦🟦🟦⬜🟦🟦🟦⬜🟦🟦🟦⬜⬜🟦🟦⬜⬜🟦🟦🟦⬜🟦🟦🟦🟦🟦🟦🟦🟦⬜🟦🟦🟦⬜⬜⬜⬜🟦🟦🟦🟦
⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
*/
