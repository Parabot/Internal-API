package org.parabot;

import org.junit.Assert;
import org.junit.Test;
import org.parabot.api.notifications.NotificationManager;

/**
 * @author JKetelaar
 */
public class NotificationTest {
    @Test
    public void testAmount(){
        NotificationManager manager = new NotificationManager();
        Assert.assertTrue(manager.getNotificationTypes().size() > 0);
    }
}
