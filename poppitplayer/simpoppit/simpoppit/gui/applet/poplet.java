package simpoppit.gui.applet;

import java.awt.*;

import javax.swing.*;

import java.util.Random;
import simpoppit.gameboard.*;
import simpoppit.gui.BalloonButton;
import simpoppit.gui.GameEvent;
import simpoppit.gui.GameLabel;
import simpoppit.gui.GameListener;
import simpoppit.gui.MouseHandler;
import simpoppit.gui.MouseMotionHandler;

/**
 * @author Andrew W. Henry
 *
 */
public class poplet extends JApplet implements GameListener{

	public void init(){
        
        this.game = new GameInterface();
        
		Container contentPane = getContentPane();
        
        InfoPanel iPanel  = new InfoPanel();
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
        iPanel.add(new JLabel("Maximum score for this board: " + this.game.getMaxScore()));
        contentPane.add(iPanel, BorderLayout.NORTH);
        
		PoppitPanel panel = new PoppitPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(this.game.getGridSize().getY()+1, this.game.getGridSize().getX()+1));
		for (Coord t : this.game.getGridAsList()){
			BalloonButton balloon = new BalloonButton(this.game, t);
			balloon.addMouseMotionListener(new MouseMotionHandler());
			balloon.addMouseListener(new MouseHandler());
			panel.add(balloon);
            this.game.addGameListener(balloon);
		}
        contentPane.add(panel);
        
        this.game.addGameListener(this);
	}
    
    public void gameEventReceived(GameEvent event){
        if (event.getAction() == "gameover"){
            int answer = JOptionPane.showConfirmDialog(this, "Out of moves!  Play another game?");
            if (answer == JOptionPane.YES_OPTION) {
                this.game.resetGame();
            }else if (answer == JOptionPane.NO_OPTION) {
            }
        }
    }
	
    private GameInterface game;
	static final long serialVersionUID = new Random().nextInt(50000);
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
