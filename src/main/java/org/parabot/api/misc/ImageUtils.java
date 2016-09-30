package org.parabot.api.misc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Fryslan
 */
public class ImageUtils {

    /**
     * Get's an image from local device
     *
     * @param location Location of the image
     * @return image from location.
     */
    public Image getLocalImage(String location) {
        try {
            File sourceimage = new File(location);
            return ImageIO.read(sourceimage);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get's an image from the given URL.
     *
     * @param url Url of th image.
     * @return image from location.
     */
    public Image getURLImage(String url) {
        try {
            URL sourceimage = new URL(url);
            return ImageIO.read(sourceimage);
        } catch (IOException e) {
            return null;
        }
    }
}
