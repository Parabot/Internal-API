package org.parabot.api.notifications;

import org.parabot.api.notifications.types.MacNotificationType;
import org.parabot.api.notifications.types.NotificationType;
import org.parabot.api.notifications.types.PushBulletNotificationType;

import java.util.ArrayList;

/**
 * @author JKetelaar
 */
public class NotificationManager {

    private static NotificationManager context;

    private ArrayList<NotificationType> notificationTypes;
    private ArrayList<NotificationType> enabledTypes;

    public NotificationManager() {
        this.notificationTypes = new ArrayList<>();
        this.enabledTypes = new ArrayList<>();

        this.fillNotificationTypes();
    }

    public ArrayList<NotificationType> getNotificationTypes() {
        return notificationTypes;
    }

    public void addNotificationType(NotificationType type) {
        this.notificationTypes.add(type);
    }

    private void fillNotificationTypes() {
        this.notificationTypes.add(new MacNotificationType());
        this.notificationTypes.add(new PushBulletNotificationType());
    }

    public void enableNotificationType(NotificationType type) {
        this.enabledTypes.add(type);
        type.enable();
    }

    public void sendNotification(String message) {
        for (NotificationType notificationType : this.enabledTypes) {
            notificationType.notify(message);
        }
    }

    public void sendNotification(String header, String message) {
        for (NotificationType notificationType : this.enabledTypes) {
            notificationType.notify(header, message);
        }
    }

    public void sendNotification(String title, String header, String message) {
        for (NotificationType notificationType : this.enabledTypes) {
            notificationType.notify(title, header, message);
        }
    }

    public ArrayList<NotificationType> getAvailableNotificationTypes() {
        ArrayList<NotificationType> types = new ArrayList<>();
        for (NotificationType notificationType : this.notificationTypes) {
            boolean inAdded = false;
            for (NotificationType enabledType : this.enabledTypes) {
                if (enabledType.getName().equalsIgnoreCase(notificationType.getName())) {
                    inAdded = true;
                }
            }

            if (!inAdded) {
                types.add(notificationType);
            }
        }

        return types;
    }

    public NotificationType getNotificationType(String name) {
        for (NotificationType notificationType : this.notificationTypes) {
            if (notificationType.getName().equalsIgnoreCase(name)) {
                return notificationType;
            }
        }

        return null;
    }

    public static NotificationManager getContext() {
        if (context == null) {
            context = new NotificationManager();
        }

        return context;
    }
}
