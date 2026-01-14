package frc.robot;


/**
 * A simple startup Banner to run when the code starts Additionally runs checks and presents
 * warnings to the console at startup
 * 
 * @see System.out.println
 */
public class Rizzler {

    public static void rizz() {
        /*
         * System.out.
         * println("  ___   _           _     _   _                                       \r\n" + //
         * " |_ _| | | ___  ___| |_  | |_| |__   ___    __ _  __ _ _ __ ___   ___ \r\n" + //
         * "  | |  | |/ _ \\/ __| __| | __| '_ \\ / _ \\  / _` |/ _` | '_ ` _ \\ / _ \\\r\n" + //
         * "  | |  | | (_) \\__ \\ |_  | |_| | | |  __/ | (_| | (_| | | | | | |  __/\r\n" + //
         * " |___| |_|\\___/|___/\\__|  \\__|_| |_|\\___|  \\__, |\\__,_|_| |_| |_|\\___|\r\n" + //
         * "                                           |___/                      ");
         */

        System.out.println(
                "___   _     ___  ____ _____   _____ _   _ _____    ____    _    __  __ _____ \r\n"
                        + //
                        "|_ _| | |   / _ \\/ ___|_   _| |_   _| | | | ____|  / ___|  / \\  |  \\/  | ____|\r\n"
                        + //
                        "| |  | |  | | | \\___ \\ | |     | | | |_| |  _|   | |  _  / _ \\ | |\\/| |  _|  \r\n"
                        + //
                        " | |  | |__| |_| |___) || |     | | |  _  | |___  | |_| |/ ___ \\| |  | | |___ \r\n"
                        + //
                        "|___| |_____\\___/|____/ |_|     |_| |_| |_|_____|  \\____/_/   \\_\\_|  |_|_____|");

        griddyemail();
        check_skibbidi();
    }

    /**
     * Hits the griddy on the ops
     * 
     * @version 1.0
     */
    public static void griddyemail() {
        System.out.println("\n hitting the griddy on the ops since 7627\n");
        System.out.println("\nCamera_Right\n");

    }

    // This was written on a laptop older than me

    /**
     * Checks if coach controller is enabled in constants or not Prints a warning message if it is
     * 
     * @see Constants.skiibi_mode
     * @return boolean - whether coach controller is enabled or not
     * 
     * @version 1.0
     */
    public static boolean check_skibbidi() {
        if (Constants.skibbidi_mode) {
            System.out.println("Coach controller enabled!\r\n" + //
                    "________________________________________ \r\n" + //
                    "|                                       |\r\n" + //
                    "| !!!DO NOT USE THIS CODE IN A MATCH!!! |\r\n" + //
                    "|_______________________________________|\r\n" + //
                    "                               \r\n" + //
                    "Robot may attempt to rizz up the ops");
            return true;
        } else {
            System.out.println("Coach controller disabled");
            return false;
        }
    }

}
