package org.parabot.api.io.build;

import java.net.URL;

public class BuildPath {

	public static final ParabotClassLoader classLoader;

	static {
		classLoader = new ParabotClassLoader(new URL[0], BuildPath.class.getClassLoader());
	}

	public static void add(final URL url) {
		try {
			classLoader.addURL(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}