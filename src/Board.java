import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by weijiangan on 28/11/2016.
 */
public class Board extends JPanel implements ComponentListener {
    Timer timer;
    private final int SPAWN_INTERVAL = 35;
    private final int INVULN_DUR = 30;
    private boolean PLAYGAME;
    private int frameWidth, frameHeight;
    private int LAND_HEIGHT = (int) (0.8 * frameHeight);
    private int WATER_HEIGHT = (int) (0.95 * frameHeight);
    private int MOUNTAIN_HEIGHT = (int) (0.82 * frameWidth);
    private int MOON_X = (int) (0.8 * frameHeight);
    private int MOON_Y = (int) (0.12 * frameWidth);
    private int PLAYER_X;
    private int PLAYER_Y;
    private int SNAIL_SPEED;
    private int NUM_OF_SNAILS;
    private int i = 0;
    private int score;
    private int scoreWidth;
    private Font scoreFont;
    private FontMetrics metric;
    private Random random;
    private Terrain cloud, ground, ground2, water, water2, mountain;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Iterator<Enemy> iter;

    public Board() throws Exception {
        addComponentListener(this);
        setDoubleBuffered(true);
        this.frameWidth = getWidth();
        this.frameHeight = getHeight();
        score = 0;
        random = new Random();
        setLayout(null);
        scoreWidth = 0;
        PLAYER_X = (int) (0.15 * frameWidth);
        SNAIL_SPEED = -7;
        NUM_OF_SNAILS = 5;
        enemies = new ArrayList<>();
        scoreFont = new Font("Calibri", Font.BOLD, 56);
        cloud = new Terrain(-2, "resources/Tiles/Cloud_1.png");
        cloud.scaleSprite(0.2f);
        ground = new Terrain(-5, "resources/Tiles/grassMid.png");
        ground2 = new Terrain(-5, "resources/Tiles/grassCenter.png");
        water = new Terrain(-15, "resources/Tiles/liquidWaterTop_mid.png");
        water2 = new Terrain(-15, "resources/Tiles/liquidWater.png");
        mountain = new Terrain(-1, "resources/Background/Mountains.png");
        player = new Player();
        player.setX(PLAYER_X);
        timer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PLAYGAME) {
                    cloud.nextPos();
                    ground.nextPos();
                    ground2.nextPos();
                    water.nextPos();
                    water2.nextPos();
                    mountain.nextPos();
                    player.nextFrame();
                    player.updatePos();
                    if (i == SPAWN_INTERVAL) {
                        spawnEnemies();
                        score++;
                        i = -1;
                    }
                    iter = enemies.iterator();
                    while (iter.hasNext()) {
                        Enemy tmp = iter.next();
                        if (tmp.getX() < -150) {
                            enemies.remove(tmp);
                            break;
                        }
                        tmp.nextFrame();
                        tmp.updatePos();
                    }
                    player.checkInvulnerability();
                }
                checkCollisions();
                repaint();
                i++;
            }
        });
        timer.start();
        PLAYGAME = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (PLAYGAME) {
            drawSky(g);
            drawMoon(g);
            drawCloud(g);
            drawMountain(g);
            drawLand(g);
            drawWater(g);
            drawPlayer(g);
            drawEnemies(g);
            drawHUD(g);
        } else {
            gameOver(g);
        }
    }

    private void drawSky(Graphics g) {
        g.setColor(new Color(181, 229, 216));
        g.fillRect(0, 0, frameWidth, (int) (frameHeight * 0.8));
    }

    private void drawMoon(Graphics g) {
        g.setColor(new Color(255, 235, 153));
        g.fillOval(MOON_X, MOON_Y, 100, 100);
    }

    private void drawCloud(Graphics g) {
        for (int x = cloud.getInitX(); x < frameWidth; x += cloud.getW())
            g.drawImage(cloud.getSprite(), x, (int) (frameHeight * 0.1), null);
    }

    private void drawMountain(Graphics g) {
        for (int x = mountain.getInitX(); x < frameWidth; x += mountain.getW())
            g.drawImage(mountain.getSprite(), x, MOUNTAIN_HEIGHT, null);
    }

    private void drawLand(Graphics g) {
        for (int y = LAND_HEIGHT; y < frameHeight; y += ground.getH()) {
            if (y == LAND_HEIGHT) {
                for (int x = ground.getInitX(); x < frameWidth; x += ground.getW()) {
                    g.drawImage(ground.getSprite(), x, y, null);
                }
            } else {
                for (int x = ground.getInitX(); x < frameWidth; x += ground2.getW()) {
                    g.drawImage(ground2.getSprite(), x, y, null);
                }
            }
        }
    }

    private void drawWater(Graphics g) {
        for (int y = WATER_HEIGHT; y < frameHeight; y += water.getH()) {
            if (y == WATER_HEIGHT) {
                for (int x = water.getInitX(); x < frameWidth; x += water.getW())
                    g.drawImage(water.getSprite(), x, y, null);
            } else {
                for (int x = water.getInitX(); x < frameWidth; x += water2.getW())
                    g.drawImage(water2.getSprite(), x, y, null);
            }
        }
    }

    private void drawPlayer(Graphics g) {
        if (player.isGODMODE() && player.getInvulnDur() % 2 == 0)
            return;
        g.drawImage(player.getSprite(), player.getX(), player.getY(), this);
    }

    private void drawEnemies(Graphics g) {
        iter = enemies.iterator();
        while (iter.hasNext()) {
            Enemy tmp = iter.next();
            g.drawImage(tmp.getSprite(), tmp.getX(), tmp.getY(), null);
        }
    }

    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(scoreFont);
        metric = g.getFontMetrics(scoreFont);
        scoreWidth = metric.stringWidth(String.format("%d", score));
        g.drawString(String.format("%d", score), frameWidth/2 - scoreWidth/2, 75);
        Image heart = new ImageIcon(this.getClass().getResource("resources/HUD/hud_heartFull.png")).getImage();
        //Image cross = new ImageIcon(this.getClass().getResource("resources/HUD/hud_x.png")).getImage();

        int curLives = player.getLives();
        for (int i= 0, x = 25; i < curLives; i++, x += 50) {
            g.drawImage(heart, x, 25, null);
        }
        if (curLives == 0) {
            PLAYGAME = false;
            timer.stop();
            repaint();
        }
        //g.drawImage(cross, frameWidth - 75, 25, null);
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);
        Image gameOver = new ImageIcon(this.getClass().getResource("resources/Menu/gameOver.png")).getImage();
        g.drawImage(gameOver, (frameWidth / 2) - (gameOver.getWidth(null) / 2), (frameHeight / 2) - (gameOver.getHeight(null) / 2), null);
        Font largeScoreFont = new Font("Calibri", Font.BOLD, 100);
        metric = g.getFontMetrics(scoreFont);
        FontMetrics metric2 = g.getFontMetrics(scoreFont);
        scoreWidth = metric2.stringWidth(String.format("%d", score));
        String message1 = "You failed to escape the snails!";
        String message2 = "Press space to restart";
        g.setColor(Color.WHITE);
        g.setFont(largeScoreFont);
        g.drawString(String.format("%d", score), frameWidth/2-scoreWidth, 200);
        g.setFont(scoreFont);
        g.drawString(message1, frameWidth/2-metric.stringWidth(message1)/2, 100);
        g.drawString(message2, frameWidth/2-metric.stringWidth(message2)/2, frameHeight - 100);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        timer.stop();
        frameHeight = getHeight();
        frameWidth = getWidth();
        LAND_HEIGHT = (int) (0.85 * frameHeight);
        WATER_HEIGHT = (int) (0.92 * frameHeight);
        MOUNTAIN_HEIGHT = (LAND_HEIGHT - 500);
        PLAYER_X = (int) (0.15 * frameWidth);
        PLAYER_Y = LAND_HEIGHT - player.getSprite().getHeight(null) + 5;
        player.setX(PLAYER_X);
        player.setY(PLAYER_Y);
        player.setLAND_Y(PLAYER_Y);
        MOON_X = (int) (0.8 * frameWidth);
        MOON_Y = (int) (0.08 * frameHeight);
        iter = enemies.iterator();
        while (iter.hasNext()) {
            Enemy tmp = iter.next();
            tmp.setY(LAND_HEIGHT - 81 + 5);
        }
        timer.start();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}


    public void keyPressed(KeyEvent e) throws Exception {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            if (PLAYGAME)
                player.jump(true);
            else
                restartGame();
        } else if (key == KeyEvent.VK_LEFT) {
            player.setDx(-9);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setDx(9);
        }
    }

    public void keyReleased(KeyEvent e) throws Exception {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            player.jump(false);
        } else if (key == KeyEvent.VK_LEFT) {
            player.setDx(0);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setDx(0);
        }
    }

    private void spawnEnemies() {
        if (enemies.size() < NUM_OF_SNAILS) {
            if (genEnemyChance() > 7) {
                Enemy enemy = new Enemy(frameWidth + 150, LAND_HEIGHT - 81 + 5, SNAIL_SPEED);
                enemies.add(enemy);
            }
        }
    }

    private int genEnemyChance() {
        return random.nextInt(10) + 1;
    }

    public void checkCollisions() {
        iter = enemies.iterator();
        while (iter.hasNext()) {
            Enemy tmp = iter.next();
            collisionHelper(player.getBounds(), tmp.getBounds(), player.getBI(), tmp.getBI());
        }
    }

    private void collisionHelper(Rectangle r1, Rectangle r2, BufferedImage b1, BufferedImage b2) {
        if(r1.intersects(r2)) {
            Rectangle r = r1.intersection(r2);

            int firstI = (int) (r.getMinX() - r1.getMinX()); //firstI is the first x-pixel to iterate from
            int firstJ = (int) (r.getMinY() - r1.getMinY()); //firstJ is the first y-pixel to iterate from
            int bp1XHelper = (int) (r1.getMinX() - r2.getMinX()); //helper variables to use when referring to collision object
            int bp1YHelper = (int) (r1.getMinY() - r2.getMinY());

            for(int i = firstI; i < r.getWidth() + firstI; i++) { //
                for(int j = firstJ; j < r.getHeight() + firstJ; j++) {
                    if((b1.getRGB(i, j) & 0xFF000000) != 0x00 && (b2.getRGB(i + bp1XHelper, j + bp1YHelper) & 0xFF000000) != 0x00) {
                        player.changeLives(-1);
                        if (!player.isGODMODE())
                            player.setInvulnDur(INVULN_DUR);
                        break;
                    }
                }
            }
        }
    }

    public void restartGame() {
        player.setX((int) (0.15 * frameWidth));
        player.setLives(3);
        enemies.clear();
        score = 0;
        PLAYGAME = true;
        timer.start();
    }
}