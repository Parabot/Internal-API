package org.parabot.api.misc;

/**
 * This class is used for detecting the user's operating system
 *
 * @author Everel
 */
public enum OperatingSystem {

    WINDOWS, LINUX, MAC, OTHER;

    public static final OperatingSystem getOS() {
        String str = System.getProperty("os.name").toLowerCase();
        if (str.contains("win")) {
            return OperatingSystem.WINDOWS;
        }
        if (str.contains("mac")) {
            return OperatingSystem.MAC;
        }
        if (str.contains("nix") || str.contains("nux")) {
            return OperatingSystem.LINUX;
        }
        return OperatingSystem.OTHER;
    }

}
