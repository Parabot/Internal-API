package org.parabot.api.output;

/**
 * @author Fryslan
 */
public class Logger {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Prints Error data in Red.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Error data.
     */
    public static void error(String tag, String data) {
        System.out.println(ANSI_RED + "[Error] : " + tag + " - " + data + ANSI_RESET);
    }

    /**
     * Prints Information data in Blue.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Information data.
     */
    public static void info(String tag, String data) {
        System.out.println(ANSI_BLUE + "[Info] : " + tag + " - " + data + ANSI_RESET);
    }

    /**
     * Prints Warning data in Yellow.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Warning data.
     */
    public static void warning(String tag, String data) {
        System.out.println(ANSI_YELLOW + "[Warning] : " + tag + " - " + data + ANSI_RESET);
    }

    /**
     * Prints Debug data in Green, if debugging is enabled.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Debug data.
     */
    public static void debug(String tag, String data) {
        System.out.println(ANSI_GREEN + "[Debug] : " + tag + " - " + data + ANSI_RESET);
    }
}
