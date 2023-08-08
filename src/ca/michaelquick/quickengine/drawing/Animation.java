package ca.michaelquick.quickengine.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {
    private final BufferedImage[] frames;
    private int currentFrame = 0;
    private float frameTime;
    private long timeThen = System.nanoTime();
    private boolean looping;

    public Animation(BufferedImage[] sprites, float frameTime) {
        this.frames = sprites;
        this.frameTime = frameTime;
    }

    public Animation(BufferedImage spriteSheet, int frameWidth, int frameHeight, float frameTime) {
        this(spriteSheet, frameWidth, frameHeight, 0, 0, frameTime);
    }

    public Animation(BufferedImage spriteSheet, int frameWidth, int frameHeight, int paddingX, int paddingY, float frameTime) {
        int totalFrames = (spriteSheet.getWidth() / (frameWidth + paddingX * 2)) * (spriteSheet.getHeight() / (frameHeight + paddingY * 2));
        System.out.println(totalFrames);
        frames = new BufferedImage[totalFrames];
        int framesPerRow = spriteSheet.getWidth() / (frameWidth + paddingX * 2);
        int framesPerCol = spriteSheet.getHeight() / (frameHeight + paddingY * 2);

        for (int i = 0; i < framesPerCol; i++) {
            for (int j = 0; j < framesPerRow; j++) {
                int index = i * framesPerRow + j;
                frames[index] = spriteSheet.getSubimage(j * (frameWidth + paddingX * 2) + paddingX, i * (frameHeight + paddingY * 2) + paddingY, frameWidth, frameHeight);
            }
        }

        this.frameTime = frameTime;
    }

    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(frames[currentFrame], x, y, null);

        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - timeThen;
        double seconds = elapsedTime / 1e9; // Convert nanoseconds to seconds

        if (seconds >= frameTime) {
            if (currentFrame == frames.length - 1 && looping)
                currentFrame = 0;
            else if (currentFrame < frames.length - 1)
                currentFrame++;
            timeThen = currentTime;
        }
    }

    public void setLoop(boolean looping) {
        this.looping = looping;
    }
}
