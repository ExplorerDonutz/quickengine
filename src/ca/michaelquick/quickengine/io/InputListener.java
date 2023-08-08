package ca.michaelquick.quickengine.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class InputListener implements KeyListener, MouseListener {
    private final Set<Integer> keysDown = new HashSet<>();

    public boolean isKeyDown(int keyCode) {
        return keysDown.contains(keyCode);
    }

    public Set<Integer> getKeysDown() {
        return keysDown;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysDown.remove(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void clearInput() {
        keysDown.clear();
    }
}
