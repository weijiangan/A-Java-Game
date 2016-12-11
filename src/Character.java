import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by weijiangan on 07/12/2016.
 */
class Character {
    int FRAMES = 11;
    int x, y;
    int curFrame;
    Image[] sprite;


    void nextFrame() {
        if (curFrame == (FRAMES-1))
            curFrame = 0;
        else
            curFrame++;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    Image getSprite() {
        return sprite[curFrame];
    }

    Rectangle getBounds() {
        return (new Rectangle(x, y, sprite[curFrame].getWidth(null), sprite[curFrame].getHeight(null)));
    }

    BufferedImage getBI() {
        BufferedImage bi = new BufferedImage(sprite[curFrame].getWidth(null), sprite[curFrame].getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(sprite[curFrame], 0, 0, null);
        g.dispose();
        return bi;
    }
}
