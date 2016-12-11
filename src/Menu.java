import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel{
	private static final long serialVersionUID = 1L;

    private static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public int iWIDTH = WIDTH, iHEIGHT = HEIGHT, buttonWIDTH = 273, buttonHEIGHT = 108;
    private boolean isSplash;
    private JPanel screen = new JPanel();

    final BufferedImage bg = ImageIO.read (new File(getClass().getResource("resources/Menu/BG.jpg").getPath()));
    final BufferedImage yogi = ImageIO.read (new File(getClass().getResource("resources/Menu/alienBack2.png").getPath()));
    final BufferedImage logo = ImageIO.read (new File(getClass().getResource("resources/Menu/logo.png").getPath()));
    final BufferedImage startButton = ImageIO.read (new File(getClass().getResource("resources/Menu/startButton.png").getPath()));

	public Menu (boolean isSplash) throws IOException {
		this.isSplash = isSplash;

        if (isSplash == true) {
            screen.repaint();
        }
	}

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

        //draw BG
        g.drawImage(bg, 0, 0, iWIDTH, iHEIGHT, null);

        if (iWIDTH != (WIDTH + 170)) {
            iWIDTH = iWIDTH + 1;
            iHEIGHT = iHEIGHT + 1;
        }

        //draw Yogi (our character lol)
        int yogiWIDTH = 233, yogiHEIGHT = 227;
        g.drawImage(yogi, WIDTH / 2 - (yogiWIDTH / 2), HEIGHT - yogiHEIGHT - 70, yogiWIDTH, yogiHEIGHT, null);

        //draw logo
        int logoWIDTH = 263, logoHEIGHT = 176;
        g.drawImage(logo, WIDTH / 2 - (logoWIDTH / 2), 30, logoWIDTH, logoHEIGHT, null);

        //draw start button
        buttonWIDTH = 273;
        buttonHEIGHT = 108;
        g.drawImage(startButton, WIDTH / 2 - (buttonWIDTH / 2), HEIGHT / 2 - buttonHEIGHT + 30, buttonWIDTH, buttonHEIGHT, null);

        //drawing the sun
        g.setColor(new Color(255, 231, 149));
        g.fillOval(1600 - 50 - 140, 25, 140, 140);

		repaint();
	}
}