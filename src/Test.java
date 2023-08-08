import ca.michaelquick.quickengine.Game;
import ca.michaelquick.quickengine.drawing.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Test extends Game {
    Animation anim;
    public Test() {
        super(400, 400, "Test");

        BufferedImage img;
        try {
            img = ImageIO.read(new File("src/testsprite.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        anim = new Animation(img, 64, 64, 1);
        anim.setLoop(false);



        start();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        anim.draw(g, 100, 100);

        g.draw(new Rectangle(10, 10, 10, 10));
    }

    public static void main(String[] args) {
        new Test();
    }
}
