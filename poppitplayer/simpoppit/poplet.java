import java.awt.*;
import java.util.Random;

import javax.swing.*;
/**
 * 
 */

/**
 * @author Andrew W. Henry
 *
 */
public class poplet extends JApplet{

	public void init(){
		Container contentPane = getContentPane();
		PoppitPanel panel = new PoppitPanel();
		GameInterface game = new GameInterface();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(game.getGrid().gridSize().getY()+1, game.getGrid().gridSize().getX()+1));
		for (Coord t : game.getGrid().getGridAsListByRow()){
			BalloonButton balloon = new BalloonButton(game, t);
			balloon.addMouseMotionListener(new MouseMotionHandler());
			balloon.addMouseListener(new MouseHandler());
			panel.add(balloon);
            game.addGameListener(balloon);
		}
        contentPane.add(panel);
	}
	
	static final long serialVersionUID = new Random().nextInt(50000);
}
