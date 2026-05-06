package frc.robot.setup.teleop.endaffector;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.Command;
import frc.bofalib.generic.control.ControlCommand;
import frc.robot.subsystems.controllable.Endaffector.Endaffector;
import frc.robot.subsystems.controllable.drivebase.Drivebase;
import frc.robot.subsystems.misc.indication.Indicator;
import frc.robot.subsystems.shared.gameinfo.SpecificGameInfoSupplier;

public final class Eject extends Command {
    private static enum State {
        /**
         * Bring the elevator up to to correct position
         */
        Elevator_UP,
        /**
         * Feed normally and continue running the flywheel.
         * 
         * Do until flywheel goes below required speed.
         */
        EJECT,
        /**
         * Re-ramp the flywheel back up to speed and run the feeder slowly.
         */
        ELEVATOR_DOWN
    }

    private final Indicator indicator;
    private final Endaffector endaffector;

    private Optional<State> stateOptional = Optional.empty();
    private Optional<Command> commandOptional = Optional.empty();

    public Eject(
        SpecificGameInfoSupplier gameInfoSupplier,
        Indicator indicator, 
        Drivebase drivebase, 
        Endaffector endaffector
    ) {
        this.indicator = indicator;
        this.drivebase = drivebase;
        this.endaffector = endaffector;

        this.feedCommand = new ControlCommand<>(
            feeder, 
            FeederControl.FEED_IN
        );

        addRequirements(
            indicator,
            endaffector
        );
    }

    private boolean atUpPose() {
        return endaffector.queryBoolean(EndaffectorBooleanQuery.AT_UP_POSE);
    }

    private void endCurrentCommand() {
        commandOptional.ifPresent(command -> command.end(true));
    }

    private void stepToAimAndRampUp() {
        //aimCommand.initialize();
        keepOffCommand.initialize();


        commandOptional = Optional.empty(); //of(aimCommand);
        stateOptional = Optional.of(State.AIM_AND_RAMP_UP);

        indicator.indicateElevatorUp();
    }

    private void stepToFeedAndShoot() {
        endCurrentCommand();
        keepOffCommand.end(true);
        agitateCommand.initialize();

        commandOptional = Optional.of(agitateCommand);
        stateOptional = Optional.of(State.FEED_AND_SHOOT);

        indicator.indicateEjecting();
    }

    private void stepToReRampUp() {
        endCurrentCommand();
        keepOffCommand.initialize();

        commandOptional = Optional.empty();
        stateOptional = Optional.of(State.RE_RAMP_UP);

        indicator.indicateElevatorDown();
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
