import javax.swing.*;
import java.awt.*;

/**
 * Created by weijiangan on 04/12/2016.
 */
public class Enemy {
    private final int FRAMES = 2;
    private int x, y;
    private int curFrame;
    private Image sprite[] = new Image[FRAMES];

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < FRAMES; i++) {
            sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/snailWalk" + i+1 + ".png")).getImage();
        }
        curFrame = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getSprite() {
        return sprite[curFrame];
    }

    public void nextFrame() {
        if (curFrame == (FRAMES-1))
            curFrame = 0;
        else
            curFrame++;
    }
}
