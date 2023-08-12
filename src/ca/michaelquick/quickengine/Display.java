package ca.michaelquick.quickengine;

import ca.michaelquick.quickengine.io.InputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Display implements WindowListener {
    private int width;
    private int height;

    private final JFrame frame;
    private final Canvas canvas;

    private BufferStrategy bs;
    private final BufferedImage offscreenBuffer;
    private final Graphics2D offscreenGraphics;
    private final InputListener inputListener;

    public Display(int width, int height, String title, InputListener inputListener) {
        this.width = width;
        this.height = height;

        frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        this.inputListener = inputListener;
        frame.addWindowListener(this);
        frame.setVisible(true);

        final Dimension d = new Dimension(width, height);

        canvas = new Canvas();
        canvas.setPreferredSize(d);
        canvas.setMaximumSize(d);
        canvas.addKeyListener(inputListener);

        frame.add(canvas);
        frame.pack();


        canvas.createBufferStrategy(3);
        bs = canvas.getBufferStrategy();

        offscreenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offscreenGraphics = offscreenBuffer.createGraphics();
    }

    public boolean preRender(Color backgroundColour) {
        if (bs == null) {
            return false;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(backgroundColour);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return true;
    }

    public void postRender(Graphics2D g) {
        if (bs == null)
            return;

        bs.show();

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Graphics2D getEmptyGraphics() {
        if (bs == null)
            return null;

        return (Graphics2D) bs.getDrawGraphics();
    }

    /**
     * Resizes the display
     * @param newWidth the new width
     * @param newHeight the new height
     */
    public void resize(int newWidth, int newHeight) {
        Dimension d = new Dimension(newWidth, newHeight);
        width = newWidth;
        height = newHeight;
        frame.setPreferredSize(d);
        frame.pack();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
        frame.addMouseListener(inputListener);
        frame.addKeyListener(inputListener);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Clear any inputs that were made before the window lost user's focus
        inputListener.clearInput();
        frame.removeKeyListener(inputListener);
        frame.removeMouseListener(inputListener);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
}
