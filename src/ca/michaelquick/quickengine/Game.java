package ca.michaelquick.quickengine;

import ca.michaelquick.quickengine.io.InputListener;
import ca.michaelquick.quickengine.screens.Screen;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Set;

/**
 * @author Michael Quick
 * @version 1.0, 2023/04/23
 * Used as a framework to create simple games using java swing graphics
 */
public class Game implements Runnable {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;
    private static final String DEFAULT_TITLE = "Quick Engine Application";
    protected int fps = 60;
    protected Display display;

    private Thread thread;
    private boolean running = false;
    private Screen screen;

    protected final InputListener inputListener;

    // Constructors

    public Game(int width, int height, String title) {
        inputListener = new InputListener();
        this.display = new Display(width, height, title, inputListener);
    }

    public Game(int width, int height) {
        this(width, height, DEFAULT_TITLE);
    }

    public Game(String title) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, title);
    }

    public Game() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
    }

    /**
     * Method for game's logic
     */
    public void tick() {
    }

    /**
     * Method for drawing onto the display
     *
     * @param g the Graphics2D object used to draw onto the display
     */
    public void draw(Graphics2D g) {

    }

    /**
     * Renders the game
     */
    public void render() {
        if (!display.preRender())
            return;

        Graphics2D g = display.getEmptyGraphics();

        // Draw either using screens or the game class
        if (screen == null)
            draw(g);
        else
            screen.draw(g);

        this.display.postRender(g);
    }

    /**
     * Resizes the game's display
     *
     * @param newWidth  the new width
     * @param newHeight the new height
     */
    public void resize(int newWidth, int newHeight) {
        display.resize(newWidth, newHeight);
    }

    @Override
    public void run() {
        double tickDuration = 1e9 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        int frameCount = 0;
        long lastFPSTime = System.nanoTime();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / tickDuration;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                delta--;
                frameCount++;
            }

            if (System.nanoTime() - lastFPSTime >= 1e9) {
                double fps = frameCount / ((System.nanoTime() - lastFPSTime) / 1e9);
                System.out.println("FPS: " + fps);
                frameCount = 0;
                lastFPSTime = System.nanoTime();
            }
        }

        stop();
    }


    /**
     * Starts the game
     */
    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the game
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage loadImage(URL file) {
        try {
            return ImageIO.read(file);
        } catch(IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Clip loadSound(URL file) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip in = AudioSystem.getClip();
            in.open(audioIn);
            return in;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public boolean isKeyDown(int key) {
        return inputListener.isKeyDown(key);
    }

    public Set<Integer> getKeyDown() {
        return inputListener.getKeysDown();
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Takes a screenshot of the game window
     */
    public void takeScreenshot() {
        try {
            Robot robot = new Robot();
            Rectangle bounds = new Rectangle(display.getFrame().getBounds());
            Insets insets = display.getFrame().getInsets();
            bounds.x += insets.left;
            bounds.width -= insets.left + insets.right;
            bounds.y += insets.top;
            bounds.height -= insets.top + insets.bottom;

            BufferedImage screenshot = robot.createScreenCapture(bounds);
            File output = new File("screenshot.png");
            ImageIO.write(screenshot, "png", output);
            System.out.println("Screenshot saved to: " + output.getAbsolutePath());
        } catch (AWTException | IOException ex) {
            ex.printStackTrace();
        }
    }

}
