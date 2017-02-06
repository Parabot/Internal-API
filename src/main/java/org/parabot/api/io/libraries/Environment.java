package org.parabot.api.io.libraries;

import org.parabot.api.io.WebUtil;

/**
 * @author JKetelaar
 */
public class Environment {

    /**
     * Loads library into environment
     *
     * @param library
     */
    public static void loadLibrary(Library library) {
        if (library.requiresJar()) {
            if (!library.hasJar()) {
                WebUtil.downloadFile(library.getDownloadLink(), library.getJarFile());
            }
            library.init();
        }
    }
}
