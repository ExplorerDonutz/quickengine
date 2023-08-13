package ca.michaelquick.quickengine.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class InputListener implements KeyListener, MouseListener {
    public static int LEFT_MOUSE_BUTTON = 0, MIDDLE_MOUSE_BUTTON = 1, RIGHT_MOUSE_BUTTON = 2;
    private final Set<Integer> keysDown = new HashSet<>();
    private int lastKey = -1;
    private int mouseX = 0, mouseY = 0;
    private boolean mousePressed = false;
    private final boolean[] mouseButtons = new boolean[3];

    public boolean isKeyDown(int keyCode) {
        return keysDown.contains(keyCode);
    }

    public Set<Integer> getKeysDown() {
        return keysDown;
    }

    public int getLastKey() {
        return lastKey;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }


    /**
     * Check if a mouse button is currently being pressed
     *
     * @param button the desired button: 1 = left, 2 = middle, 3 = right
     * @return true if the button is down, false otherwise
     */
    public boolean isMouseButtonDown(int button) {
        if (0 >= button && button < mouseButtons.length)
            return mouseButtons[button];
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown.add(e.getKeyCode());
        lastKey = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysDown.remove(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        mousePressed = true;

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                mouseButtons[LEFT_MOUSE_BUTTON] = true;
                break;
            case MouseEvent.BUTTON2:
                mouseButtons[MIDDLE_MOUSE_BUTTON] = true;
                break;
            case MouseEvent.BUTTON3:
                mouseButtons[RIGHT_MOUSE_BUTTON] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        mousePressed = false;

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                mouseButtons[0] = false;
                break;
            case MouseEvent.BUTTON2:
                mouseButtons[1] = false;
                break;
            case MouseEvent.BUTTON3:
                mouseButtons[2] = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void clearInput() {
        keysDown.clear();
    }
}
