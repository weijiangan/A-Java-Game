import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by weijiangan on 04/12/2016.
 */
public class Enemy extends Character {
    private int speed;

    public Enemy(int x, int y, int speed) {
        FRAMES = 2;
        this.x = x;
        this.y = y;
        this.speed = speed;
        sprite = new Image[FRAMES];
        for (int i = 0; i < FRAMES; i++) {
            sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/snailWalk" + (i+1) + ".png")).getImage();
        }
        curFrame = 0;
    }

    public void updatePos() {
        x += speed;
    }
}
