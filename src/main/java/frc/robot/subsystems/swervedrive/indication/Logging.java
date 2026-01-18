package frc.robot.subsystems.swervedrive.indication;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * The subsystem implementing logging indication. Do not use directly, use {@code Indicator} for
     * indicating status.
     * 
     * @see frc.robot.subsystems.swervedrive.Indicator
     */
    public Logging() {}

    /**
     * Indicate general debugging information.
     * 
     * @param sourceClass Name of the class indicating.
     * @param sourceMethod Name of the method indicating.
     * @param info The general debugging information to indicate.
     */
    public void indicateFine(String className, String methodName, String info) {
        LOGGER.logp(Level.FINE, info, methodName, info);
    }

    /**
     * Indicate that a command was completed.
     * 
     * @param completedCommandName The name of of the completed command. This should be the simple
     *        class name of the class of the completed command.
     */
    public void indicateCompletion(String completedCommandName) {
        LOGGER.logp(Level.INFO, completedCommandName, "end", "Command completed.");
    }

    /**
     * Indicate that a command was interrupted.
     * 
     * @param interruptedCommandName The name of the interrupted command. This should be the simple
     *        class name of the class of the completed command.
     */
    public void indicateInterruption(String interruptedCommandName) {
        LOGGER.logp(Level.WARNING, interruptedCommandName, "end", "Command interrupted");
    }
}
