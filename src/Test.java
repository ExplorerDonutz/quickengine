import ca.michaelquick.quickengine.Game;
import ca.michaelquick.quickengine.drawing.Animation;
import ca.michaelquick.quickengine.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Test extends Game {
    Animation anim;
    Animation anim2;
    public Test() {
        super(400, 400, "Test");

        setBackgroundColour(Color.CYAN);

        BufferedImage img, img2;
            img = Utils.loadImage("testsprite.png");
            img2 = Utils.loadImage("paddedtest.png");

        anim = new Animation(img, 64, 64, 0, 0,  false,1);
        anim.setLoop(true);

        anim2 = new Animation(img2, 14, 14, 0, 0, 2, 2, false, 1);

        start();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        anim.draw(g, 100, 100);
        anim2.draw(g, 150, 100);

        g.draw(new Rectangle(10, 10, 10, 10));
    }

    public static void main(String[] args) {
        new Test();
    }
}
