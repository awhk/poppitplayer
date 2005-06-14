package simpoppit.gui;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import simpoppit.gameboard.*;

/**
 * 
 */

/**
 * @author Andrew W. Henry
 * 
 */
public class SimPoppitGui extends JFrame implements GameListener {

    public SimPoppitGui(GameInterface aGame, boolean interactive) {

        this.game = aGame;
        this.isInteractive = interactive;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screensize = kit.getScreenSize();

        Container contentPane = getContentPane();

        setSize(screensize.width / 2, screensize.height / 2);
        setLocation(screensize.width / 4, screensize.height / 4);

        setTitle("SimPoppit");

        Image img = kit.getImage("icon.png");
        setIconImage(img);

        // setLayout(new GridLayout(2, 1));
        setLayout(new BorderLayout());

        InfoPanel iPanel = new InfoPanel();
        iPanel.setLayout(new FlowLayout());
        iPanel.setBackground(Color.WHITE);
        GameLabel score = new GameLabel("0", "score");
        GameLabel value = new GameLabel("", "value");
        this.game.addGameListener(score);
        this.game.addGameListener(value);
        iPanel.add(new JLabel("Score: "));
        iPanel.add(score);
        iPanel.add(new JLabel("Value for highlighted move: "));
        iPanel.add(value);
        iPanel.add(new JLabel("Maximum score for this board: "
                + game.getMaxScore()));
        contentPane.add(iPanel, BorderLayout.NORTH);

        PoppitPanel panel = new PoppitPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(
                this.game.getGridSize().getY()+1, this.game.getGridSize().getX()+1));
        for (Coord t : this.game.getGridAsList()) {
            BalloonButton balloon = new BalloonButton(this.game, t);
            if (this.isInteractive) {
                balloon.addMouseMotionListener(new MouseMotionHandler());
                balloon.addMouseListener(new MouseHandler());
            }
            panel.add(balloon);
            this.game.addGameListener(balloon);
        }
        contentPane.add(panel);

        this.game.addGameListener(this);
    }

    public GameInterface getGame() {
        return this.game;
    }

    public void gameEventReceived(GameEvent event) {
        // if (event.getAction() == "update"){
        // System.out.println("Cloning game...");
        // this.game = (GameInterface)this.game.clone();
        // }
        if (event.getAction() == "gameover" & this.isInteractive) {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Out of moves!  Play another game?");
            if (answer == JOptionPane.YES_OPTION) {
                this.game.resetGame();
            } else if (answer == JOptionPane.NO_OPTION) {
            }
        }
    }

    private GameInterface game;
    private boolean isInteractive;

    static final long serialVersionUID = new Random().nextInt(50000);

    /**
     * @param args
     */
    public static void main(String[] args) {
        SimPoppitGui frame = new SimPoppitGui(new GameInterface(), true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // frame.show();
    }

}

class PoppitPanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    static final long serialVersionUID = new Random().nextInt(50000);
}

class InfoPanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    static final long serialVersionUID = new Random().nextInt(50000);
}
