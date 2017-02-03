package org.parabot.api.notifications.types;

import com.github.sheigutn.pushbullet.Pushbullet;
import org.parabot.api.misc.OperatingSystem;

import javax.swing.*;

/**
 * @author JKetelaar
 */
public class PushBulletNotificationType extends NotificationType {

    private boolean available = false;
    private Pushbullet pushbullet;

    public PushBulletNotificationType() {
        super("PushBullet");
    }

    @Override
    public boolean isAvailable() {
        if (!OperatingSystem.getOS().equals(OperatingSystem.MAC)) {
            if (System.getProperty("java.specification.version").equalsIgnoreCase("1.8")) {
                String message = "Please insert your PushBullet API key, so we could send notifications.\nHit cancel to disable.";
                String s = JOptionPane.showInputDialog(null, message, "PushBullet API key", JOptionPane.QUESTION_MESSAGE);
                if (s != null) {
                    Pushbullet pushbullet = new Pushbullet(s);
                    this.pushbullet = pushbullet;

                    pushbullet.pushNote("Parabot", "PushBullet notifications have been enabled!");

                    this.available = true;
                }
                this.available = false;
            }
        }
        return this.available;
    }

    @Override
    public void notify(String title, String header, String message) {
        this.pushbullet.pushNote(title, message);
    }
}
