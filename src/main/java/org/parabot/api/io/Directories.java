package org.parabot.api.io;

import org.parabot.api.misc.OperatingSystem;
import org.parabot.api.output.Verboser;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

/**
 * Holds and manages Parabot's used directories
 *
 * @author Everel
 * @author JKetelaar
 * @author Matt
 */
public class Directories {

    private static Map<String, File> cached;
    private static File temp = null;

    static {
        cached = new HashMap<>();
        switch (OperatingSystem.getOS()) {
            case WINDOWS:
                cached.put("Root", new JFileChooser().getFileSystemView().getDefaultDirectory());
                break;
            default:
                cached.put("Root", new File(System.getProperty("user.home")));
                break;
        }

        Verboser.verbose("Caching directories...");
        cached.put("Root", getDefaultDirectory());
        cached.put("Workspace", new File(cached.get("Root"), "/Parabot/"));
        cached.put("Sources", new File(cached.get("Root"), "/Parabot/scripts/sources/"));
        cached.put("Compiled", new File(cached.get("Root"), "/Parabot/scripts/compiled/"));
        cached.put("Resources", new File(cached.get("Root"), "/Parabot/scripts/resources/"));
        cached.put("Settings", new File(cached.get("Root"), "/Parabot/settings/"));
        cached.put("Servers", new File(cached.get("Root"), "/Parabot/servers/"));
        cached.put("Cache", new File(cached.get("Root"), "/Parabot/cache/"));
        cached.put("Languages", new File(cached.get("Cache") + "/languages/"));
        cached.put("Screenshots", new File(cached.get("Root"), "/Parabot/screenshots/"));
        Verboser.verbose("Directories cached.");

        clearCache(259200);
    }

    /**
     * Set script bin folder
     *
     * @param f
     */
    public static void setScriptCompiledDirectory(File f) {
        if (!f.isDirectory()) {
            throw new IllegalArgumentException(f + "is not a directory.");
        }
        cached.put("Compiled", f);
    }

    /**
     * Set server bin folder
     *
     * @param f
     */
    public static void setServerCompiledDirectory(File f) {
        if (!f.isDirectory()) {
            throw new IllegalArgumentException(f + "is not a directory.");
        }
        cached.put("Servers", f);
    }

    /**
     * Returns the root directory outside of the main Parabot folder.
     *
     * @return
     */
    public static File getDefaultDirectory() {
        return cached.get("Root");
    }

    /**
     * Returns the Parabot folder.
     *
     * @return
     */
    public static File getWorkspace() {
        return cached.get("Workspace");
    }

    /**
     * Returns the script sources folder.
     *
     * @return
     */
    public static File getScriptSourcesPath() {
        return cached.get("Sources");
    }

    /**
     * Returns the compiled scripts folder.
     *
     * @return
     */
    public static File getScriptCompiledPath() {
        return cached.get("Compiled");
    }

    /**
     * Returns the scripts resources folder.
     *
     * @return
     */
    public static File getResourcesPath() {
        return cached.get("Resources");
    }

    /**
     * Returns the Parabot settings folder.
     *
     * @return
     */
    public static File getSettingsPath() {
        return cached.get("Settings");
    }

    /**
     * Returns the Parabot servers folder.
     *
     * @return
     */
    public static File getServerPath() {
        return cached.get("Servers");
    }

    /**
     * Returns the Parabot cache folder.
     *
     * @return
     */
    public static File getCachePath() {
        return cached.get("Cache");
    }

    public static File getLanguagesPath() {
        return cached.get("Languages");
    }

    /**
     * Returns the redirected Home Directory
     *
     * @return
     */
    public static File getHomeDir() {
        return cached.get("Home");
    }

    /**
     * Returns the screenshot folder.
     *
     * @return
     */
    public static File getScreenshotDir() {
        return cached.get("Screenshots");
    }

    /**
     * Validates all directories and makes them if necessary
     */
    public static void validate() {
        final File defaultPath = getDefaultDirectory();
        if (defaultPath == null || !defaultPath.exists()) {
            throw new RuntimeException("Default path not found");
        }
        final Queue<File> files = new LinkedList<File>();
        files.addAll(cached.values());
        while (files.size() > 0) {
            final File file = files.poll();
            if (!file.exists()) {
                Verboser.verbose("Generating directory: " + file.getAbsolutePath());
                file.mkdirs();
                if (!file.exists()) {
                    System.err.println("Failed to make directory: " + file.getAbsolutePath());
                }
            }
        }
    }

    public static File getTempDirectory() {
        if (temp != null) {
            return temp;
        }
        int randomNum = new Random().nextInt(999999999);
        temp = new File(getResourcesPath(), randomNum + "/");
        temp.mkdirs();
        temp.deleteOnExit();
        return temp;
    }

    /**
     * Clears the cache based on the latest modification
     *
     * @param remove A long that represents the amount of seconds that a file may have since the latest modification
     * @param force  Defines if the cache folder, within user.home, should also be removed
     */
    public static void clearCache(int remove, boolean force) {
        File[] cache = getCachePath().listFiles();
        if (cache != null) {
            for (File f : cache) {
                if (f != null && System.currentTimeMillis() / 1000 - f.lastModified() / 1000 > remove) {
                    Verboser.verbose("Clearing " + (f.isDirectory() ? "directory " : "file ") + f.getName() + " from cache...");

                    try {
                        FileUtil.delete(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void clearCache(int remove) {
        clearCache(remove, false);
    }

    public static void clearCache() {
        clearCache(0, true);
    }

    /**
     * @param file Directory to be removed
     */
    private static void removeDirectory(File file, boolean root) {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
                Verboser.verbose("Directory is deleted : "
                        + file.getAbsolutePath());
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    removeDirectory(fileDelete, false);
                }

                if (file.list().length == 0) {
                    file.delete();
                    Verboser.verbose("Directory is deleted: "
                            + file.getAbsolutePath());
                }
            }
            if (root) {
                file.delete();
            }
        } else {
            file.delete();
            Verboser.verbose("File is deleted : " + file.getAbsolutePath());
        }
    }

    /**
     * Returns an array of files with from a given directory and a given extension
     *
     * @param directory The directory where should be searched
     * @param extension The extension to be searched for, including the dot (like .json)
     * @return An array of of files that match the request
     */
    public static File[] listFilesWithExtension(File directory, final String extension) {
        return directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(extension);
            }
        });
    }

    public static File[] listJSONFiles(File directory) {
        return listFilesWithExtension(directory, ".json");
    }
}
