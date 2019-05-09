package org.parabot.api.ui;

import org.parabot.api.io.images.Images;
import org.parabot.api.misc.JavaUtil;
import org.parabot.api.misc.OperatingSystem;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

/**
 * Holds various swing util based methods
 *
 * @author Dane, JKetelaar
 */
public class SwingUtil {

    /**
     * Packs, centers, and shows the frame.
     *
     * @param f
     */
    public static void finalize(JFrame f) {
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * Adds the dock icon to mac users
     *
     * @param f
     */
    public static void setParabotIcons(JFrame f) {
        setParabotIcons(f);
    }

    /**
     * Adds the dock icon to mac users
     *
     * @param f
     */
    public static boolean setParabotIcon(JFrame f) {
        f.setIconImage(Images.getResource("/storage/images/icon.png"));

        if (OperatingSystem.getOS() == OperatingSystem.MAC) {
            Image image = Images.getResource("/storage/images/icon.png");
            try {
                if (JavaUtil.JAVA_VERSION >= 9) {
                    Class<?> taskbar = Class.forName("java.awt.Taskbar");
                    Object application = taskbar.getMethod("getTaskbar").invoke(null);
                    taskbar.getMethod("setIconImage", Image.class).invoke(application, image);
                } else {
                    Class<?> util = Class.forName("com.apple.eawt.Application");
                    Object application = util.getMethod("getApplication").invoke(null);
                    Method setDockIconImage = util.getMethod("setDockIconImage", Image.class);
                    setDockIconImage.invoke(application, Images.getResource("/storage/images/icon.png"));
                }
            } catch (Throwable t) {
                return false;
            }
        }

        return true;
    }
}