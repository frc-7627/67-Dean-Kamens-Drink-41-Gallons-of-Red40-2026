package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.Command;
import frc.bofalib.generic.control.ControlCommand;
import frc.robot.subsystems.controllable.agitator.Agitator;
import frc.robot.subsystems.controllable.agitator.AgitatorControl;
import frc.robot.subsystems.controllable.drivebase.Drivebase;
import frc.robot.subsystems.controllable.drivebase.Side;
import frc.robot.subsystems.controllable.feeder.Feeder;
import frc.robot.subsystems.controllable.feeder.FeederControl;
import frc.robot.subsystems.controllable.launcher.Launcher;
import frc.robot.subsystems.controllable.launcher.LauncherBooleanQuery;
import frc.robot.subsystems.controllable.launcher.LauncherControlVarShoot;
import frc.robot.subsystems.controllable.launcher.LauncherDomain;
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
    private final Drivebase drivebase;

    private Command launcherCommand;

    private final Command agitateCommand;

    private final Command keepOffCommand;

    private final Command feedCommand;

    //private final Command aimCommand;

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
        this.drivebase = drivebase;

        this.feedCommand = new ControlCommand<>(
            feeder, 
            FeederControl.FEED_IN
        );

        this.agitateCommand = new ControlCommand<>(
            agitator,
            AgitatorControl.TOWARD
        );

        this.keepOffCommand = new ControlCommand<>(
            agitator, 
            AgitatorControl.AWAY_MANUAL
        );

        this.launcherCommand = new ControlCommand<>(
            launcher, 
            new LauncherControlVarShoot(drivebase.getDistanceTargetterToHub(), LauncherDomain.CLOSE_ZONE)
        );

        // this.aimCommand = new ControlCommand<>(
        //     drivebase, 
        //     drivebase.getAngularDriveControl(
        //         drivebase.getLocationAngleTargetter(
        //             gameInfoSupplier.getHubPosition()
        //         )
        //     )
        // );

        addRequirements(
            indicator,
            // don't add drivebase here; we only using it in a read-only manner.
            launcher,
            agitator,
            feeder
        );
    }

    private boolean canFeedAndShoot() {
        return launcher.queryBoolean(LauncherBooleanQuery.AT_TARGET_SPEED);
    }

    private void endCurrentCommand() {
        commandOptional.ifPresent(command -> command.end(true));
    }

    private void stepToAimAndRampUp() {
        //aimCommand.initialize();
        keepOffCommand.initialize();


        commandOptional = Optional.empty(); //of(aimCommand);
        stateOptional = Optional.of(State.AIM_AND_RAMP_UP);

        indicator.indicateRamping();
    }

    private void stepToFeedAndShoot() {
        endCurrentCommand();
        keepOffCommand.end(true);
        agitateCommand.initialize();

        commandOptional = Optional.of(agitateCommand);
        stateOptional = Optional.of(State.FEED_AND_SHOOT);

        indicator.indicateShooting();
    }

    private void stepToReRampUp() {
        endCurrentCommand();
        keepOffCommand.initialize();

        commandOptional = Optional.empty();
        stateOptional = Optional.of(State.RE_RAMP_UP);

        indicator.indicateReRamping();
    }

    @Override
    public void initialize() {
        launcherCommand = new ControlCommand<>(
            launcher,
            switch (drivebase.getZone()) {
                case CLOSE -> new LauncherControlVarShoot(
                    drivebase.getDistanceTargetterToHub(), 
                    LauncherDomain.CLOSE_ZONE
                );
                case FAR_LEFT -> new LauncherControlVarShoot(
                    drivebase.getDistanceTargetterToAllianceZone(Side.LEFT), 
                    LauncherDomain.FAR_ZONE
                );
                case FAR_RIGHT -> new LauncherControlVarShoot(
                    drivebase.getDistanceTargetterToAllianceZone(Side.RIGHT), 
                    LauncherDomain.FAR_ZONE
                );
            }
        );

        launcherCommand.initialize();
        feedCommand.initialize();
        stepToAimAndRampUp();
    }

    @Override
    public void end(boolean interrupted) {
        launcherCommand.end(true);
        feedCommand.end(true);
        endCurrentCommand();

        stateOptional = Optional.empty();
        commandOptional = Optional.empty();
    }

    @Override
    public void execute() {
        launcherCommand.execute();
        feedCommand.execute();
        commandOptional.ifPresent(Command::execute);

        stateOptional.ifPresent(
            state -> { switch (state) {
                case AIM_AND_RAMP_UP -> {
                    keepOffCommand.execute();
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
                    keepOffCommand.execute();
                    if (canFeedAndShoot()) {
                        stepToFeedAndShoot();
                    }
                }
            } }
        );
    }
}
