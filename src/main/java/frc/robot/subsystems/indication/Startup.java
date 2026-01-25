package frc.robot.subsystems.indication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import static frc.robot.Constants.StartupConstants.*;

// Colloquially known as Rizzler.
public class Startup {
    private static final Logger LOGGER = Logger.getLogger(Startup.class.getSimpleName());

    /**
     * Startup messages.
     * 
     * Displays the startup banner and hits the griddy on the ops.
     */
    public void startup() {
        displayStartupBanner();
        hitGriddyOnTheOps();
    }

    /**
     * Display the startup banner.
     */
    private void displayStartupBanner() {
        try {
            final String startupBanner = Files.readString(Path.of(STARTUP_BANNER_FILE_PATH));

            System.out.println(startupBanner);
        } catch (FileNotFoundException exception) {
            LOGGER.severe(String.format(
                    "Could not display startup banner because the file at '%s' was not found!",
                    STARTUP_BANNER_FILE_PATH));
        } catch (IOException exception) {
            LOGGER.severe("Could not close startup banner file!");
        }
    }

    /**
     * Hit the griddy on the ops.
     */
    private void hitGriddyOnTheOps() {
        System.out.println("Hitting the griddy on the ops since 7627.");
    }
}
