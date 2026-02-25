package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.Command;
import frc.bofalib.generic.control.ControlCommand;
import frc.robot.subsystems.controllable.agitator.Agitator;
import frc.robot.subsystems.controllable.agitator.AgitatorControl;
import frc.robot.subsystems.controllable.drivebase.Drivebase;
import frc.robot.subsystems.controllable.feeder.Feeder;
import frc.robot.subsystems.controllable.feeder.FeederControl;
import frc.robot.subsystems.controllable.launcher.Launcher;
import frc.robot.subsystems.controllable.launcher.LauncherBooleanQuery;
import frc.robot.subsystems.controllable.launcher.LauncherControl;
import frc.robot.subsystems.misc.indication.Indicator;
import frc.robot.subsystems.shared.gameinfo.SpecificGameInfoSupplier;

public final class Score extends Command {
    private static enum State {
        /**
         * Orient to the hub, ramp up flywheel, and run feeder slowly.
         * 
         * Do until flywheel is up to speed.
         */
        AIM_AND_RAMP_UP,
        /**
         * Feed normally and continue running the flywheel.
         * 
         * Do until flywheel goes below required speed.
         */
        FEED_AND_SHOOT,
        /**
         * Re-ramp the flywheel back up to speed and run the feeder slowly.
         */
        RE_RAMP_UP
    }

    private final Indicator indicator;
    private final Launcher launcher;

    private final Command aimAndRampUpCommand;
    private final Command feedAndShootCommand;
    private final Command reRampUpCommand;

    private Optional<State> stateOptional = Optional.empty();
    private Optional<Command> commandOptional = Optional.empty();

    public Score(
        SpecificGameInfoSupplier gameInfoSupplier,
        Indicator indicator, 
        Drivebase drivebase, 
        Launcher launcher, 
        Agitator agitator, 
        Feeder feeder
    ) {
        this.indicator = indicator;
        this.launcher = launcher;

        final Command aimCommand = new ControlCommand<>(
            drivebase, 
            drivebase.getAngularDriveControl(
                drivebase.getLocationAngleTargetter(
                    gameInfoSupplier.getHubPosition()
                )
            )
        );

        final Command runLauncherCommand = new ControlCommand<>(
            launcher, 
            LauncherControl.SHOOT
        );

        final Command slowFeedCommand = new ControlCommand<>(
            feeder, 
            // TODO: feed slow control
            null
        );

        final Command feedCommand = new ControlCommand<>(
            feeder, 
            FeederControl.FEED_IN
        );

        final Command agitateCommand = new ControlCommand<>(
            agitator,
            AgitatorControl.TOWARD
        );

        this.aimAndRampUpCommand = runLauncherCommand
            .alongWith(slowFeedCommand)
            .alongWith(aimCommand);

        this.feedAndShootCommand = feedCommand
            .alongWith(agitateCommand)
            .alongWith(runLauncherCommand);

        this.reRampUpCommand = runLauncherCommand
            .alongWith(slowFeedCommand);

        addRequirements(
            indicator,
            drivebase,
            launcher,
            agitator,
            feeder
        );
    }

    private boolean canFeedAndShoot() {
        return launcher.queryBoolean(LauncherBooleanQuery.AT_SHOOT_SPEED);
    }

    private void endCurrentCommand() {
        commandOptional.ifPresent(command -> command.end(true));
    }

    private void stepToAimAndRampUp() {
        aimAndRampUpCommand.initialize();

        commandOptional = Optional.of(aimAndRampUpCommand);
        stateOptional = Optional.of(State.AIM_AND_RAMP_UP);

        indicator.indicateRamping();
    }

    private void stepToFeedAndShoot() {
        endCurrentCommand();
        feedAndShootCommand.initialize();

        commandOptional = Optional.of(feedAndShootCommand);
        stateOptional = Optional.of(State.FEED_AND_SHOOT);

        indicator.indicateShooting();
    }

    private void stepToReRampUp() {
        endCurrentCommand();
        reRampUpCommand.initialize();

        commandOptional = Optional.of(reRampUpCommand);
        stateOptional = Optional.of(State.FEED_AND_SHOOT);

        indicator.indicateReRamping();
    }

    @Override
    public void initialize() {
        stepToAimAndRampUp();
    }

    @Override
    public void end(boolean interrupted) {
        endCurrentCommand();

        stateOptional = Optional.empty();
        commandOptional = Optional.empty();
    }

    @Override
    public void execute() {
        commandOptional.ifPresent(Command::execute);

        stateOptional.ifPresent(
            state -> { switch (state) {
                case AIM_AND_RAMP_UP -> {
                    if (canFeedAndShoot()) {
                        stepToFeedAndShoot();
                    }
                }
                case FEED_AND_SHOOT -> {
                    if (!canFeedAndShoot()) {
                        stepToReRampUp();
                    }
                }
                case RE_RAMP_UP -> {
                    if (canFeedAndShoot()) {
                        stepToFeedAndShoot();
                    }
                }
            } }
        );
    }
}
