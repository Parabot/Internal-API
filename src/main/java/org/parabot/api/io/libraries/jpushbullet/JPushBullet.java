package org.parabot.api.io.libraries.jpushbullet;

import org.parabot.api.Configuration;
import org.parabot.api.io.Directories;
import org.parabot.api.io.build.BuildPath;
import org.parabot.api.io.libraries.Library;

import java.io.File;
import java.net.URL;

/**
 * @author EmmaStone
 */
public class JPushBullet extends Library {
    private static boolean valid;

    @Override
    public void init() {
        if (!hasJar()) {
            System.err.println("Failed to load jpushbullet... [jar missing]");
            return;
        }
        BuildPath.add(getJarFileURL());

        try {
            Class.forName("com.github.sheigutn.pushbullet.Pushbullet");
            valid = true;
        } catch (ClassNotFoundException e) {
            System.err
                    .println("Failed to add jpushbullet to build path, or incorrupt download");
        }
    }

    @Override
    public boolean isAdded() {
        return valid;
    }

    @Override
    public File getJarFile() {
        return new File(Directories.getCachePath(), "jpushbullet.jar");
    }

    @Override
    public URL getDownloadLink() {
        try {
            return new URL(Configuration.LIBRARIES_DOWNLOAD + "/JPushBullet");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean requiresJar() {
        return true;
    }

    @Override
    public String getLibraryName() {
        return "JPushBullet";
    }

    public static boolean isValid() {
        return valid;
    }
}
