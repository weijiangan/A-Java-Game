import javax.swing.*;
import java.awt.*;

/**
 * Created by weijiangan on 02/12/2016.
 */

public class Player {
    private int x, y;
    private int dx;
    private int velocity;
    private int curFrame;
    private final int FRAMES = 11;
    private boolean JUMPING;
    private boolean PEAKED;
    private int LAND_Y;
    private Image[] sprite = new Image[FRAMES];
    private Image jumpSprite;

    public Player() {
        this(0, 0);
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < FRAMES; i++) {
            sprite[i] = new ImageIcon(this.getClass().getResource("resources/Player/p3_walk/PNG/p3_walk" + String.format("%02d", i+1) + ".png")).getImage();
        }
        jumpSprite = new ImageIcon(this.getClass().getResource("resources/Player/p3_jump.png")).getImage();
        this.curFrame = 0;
        JUMPING = false;
        velocity = 40;
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

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setLAND_Y(int LAND_Y) {
        this.LAND_Y = LAND_Y;
    }

    public Image getSprite() {
        if (JUMPING) {
            return jumpSprite;
        } else {
            return sprite[curFrame];
        }
    }

    public void updatePos() {
        x += dx;
        if (JUMPING && !PEAKED) {
            if (velocity > 0) {
                y -= velocity;
                velocity *= 0.8;
            } else {
                PEAKED = true;
                velocity = 0;
            }
        } else {
            if ((y + velocity) < LAND_Y) {
                if (!PEAKED) {
                    velocity = 0;
                    PEAKED = true;
                }
                y += velocity;
                velocity += 3;
            } else {
                y = LAND_Y;
                PEAKED = false;
                velocity = 40;
            }
        }
    }

    public void jump(boolean b) {
        if (b) {
            if (!JUMPING && !PEAKED)
                LAND_Y = y;
            JUMPING = true;
        } else JUMPING = false;
    }

    public void nextFrame() {
        if (curFrame == (FRAMES-1))
            curFrame = 0;
        else
            curFrame++;
    }
}
