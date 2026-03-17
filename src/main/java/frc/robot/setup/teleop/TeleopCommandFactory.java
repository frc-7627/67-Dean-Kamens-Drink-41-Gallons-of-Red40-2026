package frc.robot.setup.teleop;

import static edu.wpi.first.units.Units.FeetPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Degrees;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.bofalib.generic.control.ControlCommand;
import frc.robot.RobotSong;
import static frc.robot.Constants.USE_TARGET_COMPENSATION;
import static frc.robot.Constants.LauncherConstants.PITCH_ANGLE_DEGREES;
import frc.robot.commands.IndicatingWrapperCommand;
import frc.robot.commands.LoggingWrapperCommand;
import frc.robot.commands.RobotSongCommand;
import frc.robot.commands.Rocker;
import frc.robot.commands.Score;
import frc.robot.commands.control.ToggleControlState;
import frc.robot.commands.drive.misc.*;
import frc.robot.subsystems.controllable.agitator.AgitatorControl;
import frc.robot.subsystems.controllable.feeder.FeederControl;
import frc.robot.subsystems.controllable.intake.IntakeControl;
import frc.robot.subsystems.controllable.launcher.LauncherControlSimple;
import frc.robot.subsystems.controllable.launcher.LauncherDomain;
import frc.robot.subsystems.controllable.swivel.SwivelControl;
import frc.robot.subsystems.controllable.drivebase.DistanceTargetter;
import frc.robot.subsystems.controllable.drivebase.Side;
import frc.robot.subsystems.controllable.drivebase.Zone;

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
        context.indicator())
    ),

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
            context.drivebase().getLocationSupplierAngleTargetter(
                () -> switch (context.drivebase().getZone()) {
                    case CLOSE -> context.gameInfoSupplier().getHubPosition();
                    case FAR_LEFT -> context.gameInfoSupplier().getFerryTargetPosition(Side.LEFT);
                    case FAR_RIGHT -> context.gameInfoSupplier().getFerryTargetPosition(Side.RIGHT);
                }
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
                context.drivebase().getLocationSupplierAngleTargetter(
                    () -> {
                        final Zone zone = context.drivebase().getZone();
                        
                        final Translation2d targetPosition = switch (zone) {
                            case CLOSE -> context.gameInfoSupplier()
                                .getHubPosition();
                            case FAR_LEFT -> context.gameInfoSupplier()
                                .getFerryTargetPosition(Side.LEFT);
                            case FAR_RIGHT -> context.gameInfoSupplier()
                                .getFerryTargetPosition(Side.RIGHT);
                        };

                        final DistanceTargetter targetter = context.drivebase()
                            .getDistanceTargetterToZone(zone);

                        final LauncherDomain domain = switch (zone) {
                            case CLOSE -> LauncherDomain.CLOSE_ZONE;
                            case FAR_LEFT -> LauncherDomain.FAR_ZONE;
                            case FAR_RIGHT -> LauncherDomain.FAR_ZONE;
                        };

                        /**
                         * Time of flight = distance to target / (shoot velocity * cos(pitch angle))
                         */
                        final double timeOfFlight = targetter.getTargetMeters() 
                            / (MetersPerSecond.convertFrom(context.launcher()
                                .getShootVelocityFPS(targetter, domain), FeetPerSecond) 
                                * Math.cos(Radians.convertFrom(PITCH_ANGLE_DEGREES, Degrees)))
                        ;

                        /**
                         * Compensate the target location to account for robot motion
                         * 
                         * delta target x = -robot velocity x component / time of flight
                         * delta target y = -robot velocity y component / time of flight
                         */
                        final double xCompensationMeters = -context.drivebase()
                            .getFieldRelativeSpeeds().vxMetersPerSecond 
                            / timeOfFlight
                        ;
                        
                        final double yCompensationMeters = -context.drivebase()
                            .getFieldRelativeSpeeds().vyMetersPerSecond 
                            / timeOfFlight
                        ;

                        final Logger logger = Logger.getLogger(TeleopCommandFactory.class.getName());

                        logger.fine("Target X compensation: " + xCompensationMeters + " meters");
                        logger.fine("Target Y compensation: " + yCompensationMeters + " meters");
                        
                        return targetPosition.plus(USE_TARGET_COMPENSATION ? new Translation2d(
                            xCompensationMeters,
                            yCompensationMeters
                        ) : new Translation2d());
                    }
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
    .alongWith(new ControlCommand<>(context.intake(), IntakeControl.LOAD))
    //.alongWith(new Lock(context.drivebase())) //TODO: ADD BACK IF WE DECIDE WE NEED IT DURING GPK
),

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
