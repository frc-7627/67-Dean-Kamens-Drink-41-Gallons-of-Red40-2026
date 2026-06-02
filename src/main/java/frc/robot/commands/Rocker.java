package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.Command;
import frc.bofalib.generic.control.ControlCommand;
import frc.robot.subsystems.controllable.swivel.*;

public final class Rocker extends Command {
    private static enum State {
        /**
         * Rock the Swivel up to dislodge any stuck balls
         * 
         * Do this until the position specified in constants is met
         */
        ROCK_UP,
        /**
         * Rock the Swivel down to dislodge any stuck balls
         * 
         * Do this until the position specified in constants is met
         */
        ROCK_DOWN,
    }

    private final Swivel swivel;

    private final Command rockIn;

    private final Command rockOut;
    
    private Optional<State> stateOptional = Optional.empty();
    private Optional<Command> commandOptional = Optional.empty();

    public Rocker( 
        Swivel swivel
    ) {

        this.swivel = swivel;

        this.rockIn = new ControlCommand<>(
            swivel, 
            SwivelControl.FOLD_IN
        );

        this.rockOut = new ControlCommand<>(
            swivel, 
            SwivelControl.FOLD_OUT
        );

        addRequirements(
            swivel
        );
    }

    private boolean isIn() {
        return swivel.queryBoolean(SwivelBooleanQuery.AT_UP_POSE);
    }

    private boolean isOut() {
        return swivel.queryBoolean(SwivelBooleanQuery.AT_DOWN_POSE);
    }

    private void endCurrentCommand() {
        commandOptional.ifPresent(command -> command.end(true));
    }

    private void stepToRockUp() {
        endCurrentCommand();
        rockIn.initialize();

        commandOptional = Optional.of(rockIn);
        stateOptional = Optional.of(State.ROCK_UP);

    }

    private void stepToRockDown() {
        endCurrentCommand();
        rockOut.initialize();
    
        commandOptional = Optional.of(rockOut);
        stateOptional = Optional.of(State.ROCK_DOWN);

    }

    @Override
    public void initialize() {
        stepToRockUp();
    }

    @Override
    public void end(boolean interrupted) {
        rockOut.end(true);
        endCurrentCommand();

        stateOptional = Optional.empty();
        commandOptional = Optional.empty();
    }

    @Override
    public void execute() {

        stateOptional.ifPresent(
            state -> { switch (state) {
                case ROCK_UP -> {
                    rockIn.execute();
                    if (isIn()) {
                        stepToRockDown();
                    }
                }
                case ROCK_DOWN -> {
                    rockOut.execute();
                    if (isOut()) {
                        stepToRockUp();
                    }
                }
            } }
        );
    }
}
