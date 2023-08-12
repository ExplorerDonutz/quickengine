package ca.michaelquick.quickengine.utils;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Contains some useful methods for creating games
 */
public final class Utils {

    /**
     * Loads an image using its url
     * @param fileUrl the url of the image
     * @throws IllegalArgumentException if the image is not found
     * @return the BufferedImage loaded from the url
     */
    public static BufferedImage loadImage(String fileUrl) {
        try {
            URL url = Utils.class.getClassLoader().getResource(fileUrl);
            if (url == null) {
                throw new IllegalArgumentException("Image not found: " + fileUrl);
            }

            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Clip loadSound(String fileUrl) {
        try {
            URL url = Utils.class.getClassLoader().getResource(fileUrl);

            if (url == null) {
                throw new IllegalArgumentException("Audio not found: " + fileUrl);
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip in = AudioSystem.getClip();
            in.open(audioIn);
            return in;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
