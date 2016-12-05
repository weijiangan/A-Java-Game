import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by weijiangan on 28/11/2016.
 */
public class Game implements ActionListener, KeyListener {
    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static JFrame f;
    private JPanel topPanel;
    private JButton pauseButton;
    private Board board;
    private Timer timer;
    private Clip clip;

    private Game() {
        initGame();
    }

    private void initGame() {
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

    private JPanel createContentPane() {
        topPanel = new JPanel();    // topmost JPanel in layout hierarchy
        topPanel.setBackground(Color.BLACK);
        // Allow us to layer the panels
        LayoutManager overlay = new OverlayLayout(topPanel);
        topPanel.setLayout(overlay);

        // Start Game JButton
        pauseButton = new JButton("Resume Playing");
        pauseButton.setFocusable(false); // rather than just setFocusable(false)
        pauseButton.setFont(new Font("Calibri", Font.BOLD, 42));
        pauseButton.setAlignmentX(0.5f); // center horizontally on-screen
        pauseButton.setAlignmentY(0.5f); // center vertically on-screen
        pauseButton.addActionListener(this);
        topPanel.add(pauseButton);

        // Must add last to ensure button's visibility
        board = new Board(SCREEN_WIDTH, SCREEN_HEIGHT);
        topPanel.add(board);

        return topPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        board.keyPressed(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (board.timer.isRunning())
                board.timer.stop();
            else
                board.timer.start();
        } else {
            board.keyReleased(e);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();
                f.setVisible(true);
            }
        });
    }
}
