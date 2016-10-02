package org.parabot.api.output;

/**
 * @author Fryslan
 */
public class Logger {

    public enum LoggerColor {
        RESET("\u001B[0m"),
        BLACK("\u001B[30m"),
        WHITE("\u001B[37m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m");

        private final String ansi;

        LoggerColor(String ansi) {
            this.ansi = ansi;
        }

        public String getAnsi() {
            return ansi;
        }
    }


    /**
     * Prints Error data in Red.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Error data.
     */
    public static void error(String tag, String data) {
        System.out.println(LoggerColor.RED + "[Error] : " + tag + " - " + data + LoggerColor.RESET);
    }

    /**
     * Prints Error data in Red.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Error data.
     * @param throwable Error or Exception.
     */
    public static void error(String tag, String data, Throwable throwable) {
        System.out.println(LoggerColor.RED + "[Error] : " + tag + " - " + data);
        throwable.printStackTrace();
        System.out.println(LoggerColor.RESET);

    }

    /**
     * Prints Information data in Blue.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Information data.
     */
    public static void info(String tag, String data) {
        System.out.println(LoggerColor.BLUE + "[Info] : " + tag + " - " + data + LoggerColor.RESET);
    }

    /**
     * Prints Warning data in Yellow.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Warning data.
     */
    public static void warning(String tag, String data) {
        System.out.println(LoggerColor.YELLOW + "[Warning] : " + tag + " - " + data + LoggerColor.RESET);
    }

    /**
     * Prints Debug data in Green, if debugging is enabled.
     *
     * @param tag  Reference to the location , I.E Core, UserInterface.
     * @param data Debug data.
     */
    public static void debug(String tag, String data) {
        System.out.println(LoggerColor.GREEN + "[Debug] : " + tag + " - " + data + LoggerColor.RESET);
    }

    /**
     * @param tag    Reference to the location , I.E Core, UserInterface.
     * @param data   Data to Print.
     * @param color  Color to print the the data in.
     * @param header Reference to the reason this message is printed I.E WARNING, DEBUG.
     */
    public static void custom(String tag, String data, LoggerColor color, String header) {
        System.out.println(color + "[" + header.toUpperCase() + "] : " + tag + " - " + data + LoggerColor.RESET);
    }
}
