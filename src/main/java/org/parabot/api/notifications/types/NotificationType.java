package org.parabot.api.notifications.types;

import org.parabot.api.Configuration;

/**
 * @author JKetelaar
 */
public abstract class NotificationType {

    private String name;

    public NotificationType(String name) {
        this.name = name;
    }

    public abstract boolean isAvailable();

    public abstract void notify(String title, String header, String message);

    public void notify(String header, String message){
        notify(Configuration.BOT_TITLE, header, message);
    }

    public void notify(String message){
        notify("Notification", message);
    }

    public String getName() {
        return name;
    }
}
