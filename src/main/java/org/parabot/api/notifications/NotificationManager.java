package org.parabot.api.notifications;

import org.parabot.api.notifications.types.MacNotificationType;
import org.parabot.api.notifications.types.NotificationType;
import org.parabot.api.notifications.types.PushBulletNotificationType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JKetelaar
 */
public class NotificationManager {

    private static NotificationManager context;

    private ArrayList<NotificationType> notificationTypes;

    public NotificationManager() {
        this.notificationTypes = new ArrayList<>();
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

        PushBulletNotificationType pushBullet = new PushBulletNotificationType();
        if (pushBullet.isAvailable()){
            this.notificationTypes.add(pushBullet);
        }
    }

    public ArrayList<NotificationType> getAvailableNotificationTypes() {
        ArrayList<NotificationType> types = new ArrayList<>();
        for (NotificationType notificationType : this.notificationTypes) {
            if (notificationType.isAvailable()) {
                types.add(notificationType);
            }
        }

        return types;
    }

    public NotificationType getFirstAvailableNotificationType() {
        List<NotificationType> types = getAvailableNotificationTypes();

        return types.size() > 0 ? types.get(0) : null;
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
