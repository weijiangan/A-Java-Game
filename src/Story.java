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
    private static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private int counter = 0;

    private JPanel screen = new JPanel();

    final BufferedImage one = ImageIO.read (new File(getClass().getResource("resources/Story/1.jpg").getPath()));
    final BufferedImage two = ImageIO.read (new File(getClass().getResource("resources/Story/2.jpg").getPath()));
    final BufferedImage three = ImageIO.read (new File(getClass().getResource("resources/Story/3.jpg").getPath()));
    final BufferedImage four = ImageIO.read (new File(getClass().getResource("resources/Story/4.jpg").getPath()));
    final BufferedImage five = ImageIO.read (new File(getClass().getResource("resources/Story/5.jpg").getPath()));
    final BufferedImage six = ImageIO.read (new File(getClass().getResource("resources/Story/6.jpg").getPath()));
    final BufferedImage black = ImageIO.read (new File(getClass().getResource("resources/Story/Game.jpg").getPath()));

    public Story() throws IOException {
        screen.addKeyListener(this);
        screen.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (counter) {
            case 0:
                g.drawImage(one, 0, 0, WIDTH, HEIGHT, null);
                break;

            case 1:
                g.drawImage(two, 0, 0, WIDTH, HEIGHT, null);
                break;

            case 2:
                g.drawImage(three, 0, 0, WIDTH, HEIGHT, null);
                break;

            case 3:
                g.drawImage(four, 0, 0, WIDTH, HEIGHT, null);
                break;

            case 4:
                g.drawImage(five, 0, 0, WIDTH, HEIGHT, null);
                break;

            case 5:
                g.drawImage(six, 0, 0, WIDTH, HEIGHT, null);
                break;

            case 6:
                g.drawImage(black, 0, 0, WIDTH, HEIGHT, null);
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                if (counter < 6) {
                    counter++;
                }
                break;

            case KeyEvent.VK_LEFT:
                if (counter > 0) {
                    counter--;
                }
                break;

            case KeyEvent.VK_SPACE:
                break;
        }
    }

    public int startGame () {
        return 10;
    }
}
