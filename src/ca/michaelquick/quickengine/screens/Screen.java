package ca.michaelquick.quickengine.screens;

import java.awt.*;

public interface Screen {
    public void draw(Graphics2D g);
    public void tick();
    public void resize();
}
