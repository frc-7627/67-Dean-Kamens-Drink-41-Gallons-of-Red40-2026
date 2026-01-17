package frc.robot.subsystems.swervedrive;

import frc.robot.subsystems.swervedrive.Logging;

public class Indicator {
    Logging logging;

    /**
     * The subsystem for indicating any status.
     * 
     * Delegates implementation details to other subsystems(e.g. logging, led, etc.).
     * 
     * @param logging The logging subsystem.
     */
    public Indicator(Logging logging) {
        this.logging = logging;
    }

    /**
     * Indicate general debugging information.
     * 
     * @param sourceClass Name of the class indicating.
     * @param sourceMethod Name of the method indicating.
     * @param info The general debugging information to indicate.
     */
    public void indicateFine(String sourceClass, String sourceMethod, String info) {
        logging.indicateFine(sourceClass, sourceMethod, info);
    }

    /**
     * Indicate idle status.
     */
    public void indicateIdle() {

    }

    /**
     * Indicate that a command was completed. This should only be called in a command's {@code end}
     * method.
     * 
     * @param completedCommandName The name of of the completed command. This should be the simple
     *        class name of the class of the completed command.
     */
    public void indicateCompletion(String completedCommandName) {
        logging.indicateCompletion(completedCommandName);
    }

    /**
     * Indicate that a command was interrupted. This should only be called in a command's
     * {@code end} method.
     * 
     * @param interruptedCommandName The name of the interrupted command. This should be the simple
     *        class name of the class of the completed command.
     */
    public void indicateInterruption(String interruptedCommandName) {
        logging.indicateInterruption(interruptedCommandName);
    }
}
