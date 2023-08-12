package ca.michaelquick.quickengine.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

/**@author Michael Quick
 * @version 1.0, 2023/08/11
 * Animations that can be drawn and played at different speeds
 */
public class Animation {
    private final BufferedImage[] frames;
    private int currentFrame = 0;
    private float frameTime;
    private long timeThen = System.nanoTime();
    private boolean looping;

    /**
     * Creates an animation using an array of sprite images
     *
     * @param sprites   an array of buffered images that consist of the frames or sprites for the animation
     * @param frameTime the amount of time each frame should appear on the screen for
     */
    public Animation(BufferedImage[] sprites, float frameTime) {
        this.frames = sprites;
        this.frameTime = frameTime;
    }

    /**
     * Creates an animation using a spritesheet image
     *
     * @param spriteSheet the image that is to be cut into animation frames
     * @param frameWidth  the width of an individual frame
     * @param frameHeight the height of an individual frame
     * @param frameTime   the amount of time each frame should appear on the screen for
     */
    public Animation(BufferedImage spriteSheet, int frameWidth, int frameHeight, float frameTime) {
        this(spriteSheet, frameWidth, frameHeight, 0, 0, frameTime);
    }

    /**
     * Creates an animation using a spritesheet image with frame padding
     *
     * @param spriteSheet the image that is to be cut into animation frames
     * @param frameWidth  the width of an individual frame
     * @param frameHeight the height of an individual frame
     * @param paddingX    the x value of the padding used
     * @param paddingY    the y value of the padding used
     * @param frameTime   the amount of time each frame should appear on the screen for
     */
    public Animation(BufferedImage spriteSheet, int frameWidth, int frameHeight, int paddingX, int paddingY, float frameTime) {
        int totalFrames = (spriteSheet.getWidth() / (frameWidth + paddingX * 2)) * (spriteSheet.getHeight() / (frameHeight + paddingY * 2));
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

    public Animation(BufferedImage spritesheet, int frameWidth, int frameHeight, int initialLine, int finalLine, boolean isVertical, float frameTime) {
        this(spritesheet, frameWidth, frameHeight, initialLine, finalLine, 0, 0, isVertical, frameTime);
    }

    /**
     * Creates an animation using a specified section of the given spritesheet image
     * @param spriteSheet the image that is to be cut into animation frames
     * @param frameWidth  the width of an individual frame
     * @param frameHeight the height of an individual frame
     * @param initialLine the first line of the image to use (between 0 and the final line - 1)
     * @param finalLine   the last line of the image to use (between 0 and the final line - 1)
     * @param paddingX    the x value of the padding used
     * @param paddingY    the y value of the padding used
     * @param isVertical  whether the sprite sheet is ordered horizontally (rows) or vertically (columns)
     * @param frameTime   the amount of time that each frame should appear on the screen for
     */
    public Animation(BufferedImage spriteSheet, int frameWidth, int frameHeight, int initialLine, int finalLine, int paddingX, int paddingY, boolean isVertical, float frameTime) {
        if (isVertical) {
            int initialX = initialLine * (frameWidth + 2 * paddingX);
            int width = ((finalLine + 1) * (frameWidth + 2 * paddingX)) - initialX;
            spriteSheet = spriteSheet.getSubimage(initialX, 0, width, spriteSheet.getHeight());

            int totalFrames = (spriteSheet.getWidth() / (frameWidth + paddingX * 2)) * (spriteSheet.getHeight() / (frameHeight + paddingY * 2));
            frames = new BufferedImage[totalFrames];
            int framesPerRow = spriteSheet.getWidth() / (frameWidth + paddingX * 2);
            int framesPerCol = spriteSheet.getHeight() / (frameHeight + paddingY * 2);

            for (int i = 0; i < framesPerRow; i++) {
                for (int j = 0; j < framesPerCol; j++) {
                    int index = i * framesPerCol + j;
                    frames[index] = spriteSheet.getSubimage(i * (frameWidth + paddingX * 2) + paddingX, j * (frameHeight + paddingY * 2) + paddingY, frameWidth, frameHeight);
                }
            }
        } else {
            int initialY = initialLine * (frameHeight + 2 * paddingY);
            int height = ((finalLine + 1) * (frameHeight + 2 * paddingY)) - initialY;
            spriteSheet = spriteSheet.getSubimage(0, initialY, spriteSheet.getWidth(), height);


            int totalFrames = (spriteSheet.getWidth() / (frameWidth + paddingX * 2)) * (spriteSheet.getHeight() / (frameHeight + paddingY * 2));
            frames = new BufferedImage[totalFrames];
            int framesPerRow = spriteSheet.getWidth() / (frameWidth + paddingX * 2);
            int framesPerCol = spriteSheet.getHeight() / (frameHeight + paddingY * 2);

            for (int i = 0; i < framesPerCol; i++) {
                for (int j = 0; j < framesPerRow; j++) {
                    int index = i * framesPerRow + j;
                    frames[index] = spriteSheet.getSubimage(j * (frameWidth + paddingX * 2) + paddingX, i * (frameHeight + paddingY * 2) + paddingY, frameWidth, frameHeight);
                }
            }
        }

        this.frameTime = frameTime;
    }


    /**
     * Draws the current frame of the animation and switches to the next frame if the frameTime has past
     * @param g the graphics object to draw onto
     * @param x the x coordinate of the animation
     * @param y the y coordinate of the animation
     */
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

    /**
     * Sets whether the animation should loop or nit
     * @param looping sets the animation to loop if true and not to loop if false
     */
    public void setLoop(boolean looping) {
        this.looping = looping;
    }
}
