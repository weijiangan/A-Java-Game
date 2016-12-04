import javax.swing.*;
import java.awt.*;

/**
 * Created by weijiangan on 03/12/2016.
 */
public class Terrain {
    private int initX;
    private int w, h;
    private int pace;
    private double ratio;
    private Image sprite;

    Terrain(int pace, String imgPath) {
        this.initX = 0;
        this.pace = pace;
        sprite = new ImageIcon(this.getClass().getResource(imgPath)).getImage();
        //ratio = tile.getWidth(null) / (frameWidth * 0.1);
        //w = (int) (tile.getWidth(null) * 1);
        //h = (int) (tile.getHeight(null) * 1);
        w = sprite.getWidth(null);
        h = sprite.getHeight(null);
    }

    public Image getSprite() {
        return sprite;
    }

    public int getInitX() {
        return initX;
    }

    public void nextPos() {
        initX += (pace);
        if (initX < (-w)) {
            initX += w;
        }
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public void scaleSprite(float factor) {
        int newW = (int) (w * factor);
        int newH = (int) (h * factor);
        sprite = sprite.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    }
}
