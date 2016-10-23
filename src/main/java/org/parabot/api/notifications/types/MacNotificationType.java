package org.parabot.api.notifications.types;

import org.parabot.api.misc.OperatingSystem;

import java.io.IOException;

/**
 * @author JKetelaar
 */
public class MacNotificationType extends NotificationType {

    public MacNotificationType() {
        super("Mac");
    }

    @Override
    public boolean isAvailable() {
        return OperatingSystem.getOS().equals(OperatingSystem.MAC);
    }

    @Override
    public void notify(String title, String header, String message) {
        final StringBuilder src = new StringBuilder();

        src.append("display notification")
                .append(" \"").append(message).append("\"");
        if (title != null && title.length() > 0) {
            src.append(" with title ").append("\"").append(title).append("\"");
        }
        if (header != null && header.length() > 0) {
            src.append(" subtitle ").append("\"").append(header).append("\"");
        }
        src.append(" sound name \"Ping\" ");

        final Runtime rt = Runtime.getRuntime();
        final String[] cmd = new String[]{"/usr/bin/osascript", "-e", src.toString()};
        try {
            rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
