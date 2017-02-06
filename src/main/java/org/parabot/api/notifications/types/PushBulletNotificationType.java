package org.parabot.api.notifications.types;

import org.parabot.api.notifications.types.pushbullet.PushBulletController;

import javax.swing.*;

/**
 * @author JKetelaar
 */
public class PushBulletNotificationType extends NotificationType {

    private boolean enabled = false;

    public PushBulletNotificationType() {
        super("PushBullet");
    }

    @Override
    public boolean isAvailable() {
        double version = 1.0;

        try {
            version = Double.parseDouble(System.getProperty("java.specification.version"));
        } catch (NumberFormatException ignored) {
        }

        return version >= 1.8;
    }

    @Override
    public void enable() {
        String message = "Please insert your PushBullet API key, so we could send notifications.";
        String s = JOptionPane.showInputDialog(null, message, "PushBullet API key", JOptionPane.QUESTION_MESSAGE);
        if (s != null) {
            this.enabled = PushBulletController.pushNote("Parabot", "PushBullets notifications have been enabled for Parabot", s);
        }
    }

    @Override
    public void notify(String title, String header, String message) {
        if (this.enabled) {
            PushBulletController.pushNote(title, message);
        }
    }
}
