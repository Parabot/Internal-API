package org.parabot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parabot.api.io.Directories;
import org.parabot.api.io.WebUtil;
import org.parabot.api.io.build.BuildPath;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BuildPathTest {

    private static final String url = "http://v3.bdn.parabot.org/api/bot/download/default-provider";
    private static File path;

    @Before
    public void setUp() throws MalformedURLException {
        Directories.validate();

        path = new File(Directories.getCachePath(), "unit-test.jar");

        WebUtil.downloadFile(new URL(url), path);
    }

    @Test
    public void testAddURL() throws MalformedURLException {
        BuildPath.add(path.toURI().toURL());

        try {
            Class<?> taskbar = BuildPath.classLoader.loadClass("org.rev317.min.Loader");
            taskbar.getDeclaredMethods();
        } catch (Exception | NoClassDefFoundError e) {
            Assert.assertEquals(e.getClass().getName(), "java.lang.NoClassDefFoundError");
        }

    }
}
