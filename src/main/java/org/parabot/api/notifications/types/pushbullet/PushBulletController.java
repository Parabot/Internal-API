package org.parabot.api.notifications.types.pushbullet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author JKetelaar
 */
public class PushBulletController {

    private static Object pushBulletInstance;

    public static void setPushBulletInstance(String key){
        ClassLoader classLoader = PushBulletController.class.getClassLoader();
        try {
            PushBulletController.pushBulletInstance = classLoader.loadClass("com.github.sheigutn.pushbullet.Pushbullet").getConstructors()[0].newInstance(key);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Object getPushBulletInstance(String key){
        PushBulletController.setPushBulletInstance(key);

        return PushBulletController.pushBulletInstance;
    }

    public static boolean pushNote(String title, String message, String key){
        PushBulletController.setPushBulletInstance(key);

        return PushBulletController.pushNote(title, message);
    }

    public static boolean pushNote(String title, String message){
        Class[] cArg = new Class[2];
        cArg[0] = String.class;
        cArg[1] = String.class;

        if (PushBulletController.pushBulletInstance != null){
            try {
                Method method = PushBulletController.pushBulletInstance.getClass().getMethod("pushNote", cArg);
                method.setAccessible(true);
                method.invoke(PushBulletController.pushBulletInstance, title, message);

                return true;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                return false;
            }
        }
        return false;
    }
}
