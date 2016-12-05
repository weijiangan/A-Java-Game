import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by weijiangan on 28/11/2016.
 */
public class Game implements ActionListener, KeyListener {
    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private int buttonWIDTH = 273, buttonHEIGHT = 108;
    private boolean INGAME;
    private static JFrame f;
    private JPanel topPanel;
    private JButton pauseButton;
    private JButton startGame;
    private Board board;
    private Menu menu;
    private Story story;
    private Timer timer;
    private Clip clip;

    private Game() throws IOException {
        initGame();
    }

    private void initGame() throws IOException {
        INGAME = false;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(getClass().getResource("resources/bgm.wav").getPath())));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception occurred: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
        f = new JFrame("A Java Game");
        f.setMinimumSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4));
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setResizable(true);
        f.setContentPane(createContentPane());
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                board.timer.stop();
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit game?", "Notice",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                board.timer.start();
            }
        });
        f.setAlwaysOnTop(false);
        f.addKeyListener(this);
        timer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topPanel.revalidate();
                topPanel.repaint();
            }
        });
    }

    private JPanel createContentPane() throws IOException {
        topPanel = new JPanel();    // topmost JPanel in layout hierarchy
        topPanel.setBackground(Color.BLACK);
        topPanel.addKeyListener(this);
        // Allow us to layer the panels
        LayoutManager overlay = new OverlayLayout(topPanel);
        topPanel.setLayout(overlay);

        //start button
        startGame = new JButton("Start Game");
        startGame.setOpaque(false);
        startGame.setPreferredSize(new Dimension(buttonWIDTH, buttonHEIGHT));
        startGame.setContentAreaFilled(false);
        startGame.setBorderPainted(false);
        startGame.setFocusable(false); // rather than just setFocusable(false)
        startGame.setAlignmentX(0.5f); // center horizontally on-screen
        startGame.setAlignmentY(0.5f); // center vertically on-screen
        startGame.addActionListener(this);

        //pause button
        pauseButton = new JButton("Resume Playing");
        pauseButton.setFocusable(false); // rather than just setFocusable(false)
        pauseButton.setFont(new Font("Calibri", Font.BOLD, 42));
        pauseButton.setAlignmentX(0.5f); // center horizontally on-screen
        pauseButton.setAlignmentY(0.5f); // center vertically on-screen
        pauseButton.addActionListener(this);
        pauseButton.setVisible(false);
        topPanel.add(pauseButton);

        // Must add last to ensure button's visibility
        menu = new Menu (true);
        story = new Story();

        topPanel.add(startGame);
        topPanel.add(menu);

        return topPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            topPanel.remove(startGame);
            topPanel.remove(menu);
            topPanel.add(story);
            topPanel.revalidate();
        } else if (e.getSource() == pauseButton) {
            if (!board.timer.isRunning()) {
                timer.stop();
                board.timer.start();
                pauseButton.setVisible(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (INGAME)
            board.keyPressed(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (board.timer.isRunning()) {
                board.timer.stop();
                timer.start();
                pauseButton.setVisible(true);
            } else {
                timer.stop();
                board.timer.start();
                pauseButton.setVisible(false);
            }
        } else if (INGAME) {
            board.keyReleased(e);
        } else if (!INGAME) {
            story.keyReleased(e);
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                topPanel.remove(story);
                board = new Board(SCREEN_WIDTH, SCREEN_HEIGHT);
                topPanel.add(board);
                topPanel.revalidate();
                INGAME = true;
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Game game = new Game();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                f.setVisible(true);
            }
        });
    }
}
