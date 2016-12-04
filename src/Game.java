import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by weijiangan on 28/11/2016.
 */
public class Game implements ActionListener, KeyListener {
    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static JFrame f;
    private JPanel topPanel;
    private JButton startGame;
    private Board board;

    private Game() {
        initGame();
    }

    private void initGame() {
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
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit game?", "Notice",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        f.setAlwaysOnTop(false);
        f.addKeyListener(this);
    }

    private JPanel createContentPane() {
        topPanel = new JPanel();    // topmost JPanel in layout hierarchy
        topPanel.setBackground(Color.BLACK);
        // Allow us to layer the panels
        LayoutManager overlay = new OverlayLayout(topPanel);
        topPanel.setLayout(overlay);

        // Start Game JButton
        startGame = new JButton("Start Playing!");
        startGame.setFocusable(false); // rather than just setFocusable(false)
        startGame.setFont(new Font("Calibri", Font.BOLD, 42));
        startGame.setAlignmentX(0.5f); // center horizontally on-screen
        startGame.setAlignmentY(0.5f); // center vertically on-screen
        startGame.addActionListener(this);
        //topPanel.add(startGame);

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
        board.keyReleased(e);
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
