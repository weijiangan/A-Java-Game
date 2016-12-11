import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sam on 12/5/16.
 */
public class Story extends JPanel implements KeyListener {
    private static int WIDTH;
    private static int HEIGHT;
    private int counter = 0;

    private Image[] scenes = new Image[] {
            new ImageIcon(this.getClass().getResource("resources/Story/0.jpg")).getImage(),
            new ImageIcon(this.getClass().getResource("resources/Story/1.jpg")).getImage(),
            new ImageIcon(this.getClass().getResource("resources/Story/2.jpg")).getImage(),
            new ImageIcon(this.getClass().getResource("resources/Story/3.jpg")).getImage(),
            new ImageIcon(this.getClass().getResource("resources/Story/4.jpg")).getImage(),
            new ImageIcon(this.getClass().getResource("resources/Story/5.jpg")).getImage(),
    };

    public Story(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
        setDoubleBuffered(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (counter != scenes.length)
            g.drawImage(scenes[counter], 0, 0, WIDTH, HEIGHT, null);
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                if (counter < 6) counter++;
                break;

            case KeyEvent.VK_LEFT:
                if (counter > 0) counter--;
                break;

            case KeyEvent.VK_SPACE:
                break;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
}
