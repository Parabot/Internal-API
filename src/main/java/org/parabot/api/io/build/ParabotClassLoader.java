package org.parabot.api.io.build;

import java.net.URL;
import java.net.URLClassLoader;

public class ParabotClassLoader extends URLClassLoader {

    public ParabotClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public void addURL(URL url) {
        super.addURL(url);
    }
}