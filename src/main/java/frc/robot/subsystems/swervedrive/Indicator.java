package frc.robot.subsystems.swervedrive;

import frc.robot.subsystems.swervedrive.Logging;
import frc.robot.subsystems.swervedrive.LED;

public class Indicator {
    Logging logging;
    LED led;

    /**
     * The subsystem for indicating any status.
     * 
     * Delegates implementation details to other subsystems(e.g. logging, led, etc.).
     * 
     * @param logging The logging subsystem.
     * @param led The LED subsystem.
     */
    public Indicator(Logging logging, LED led) {
        this.logging = logging;
        this.led = led;
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
        led.indicateIdle();
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
        led.indicateCompletion();
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
        led.indicateInterruption();
    }
}
