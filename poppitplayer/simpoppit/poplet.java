import java.awt.*;
import java.util.Random;

import javax.swing.*;
/**
 * 
 */

/**
 * @author datacomm
 *
 */
public class poplet extends JApplet{

	public void init(){
		Container contentPane = getContentPane();
		GuiTestPanel panel = new GuiTestPanel();
		GameGrid game = new GameGrid();
        panel.GameVars(game);
        panel.setBackground(Color.WHITE);
		panel.setLayout(new GridLayout(panel.getGameY()+1, panel.getGameX()+1));
		for (Coord t : game.getGridAsListByRow()){
			//panel.add(new JButton(t.toString()));
			//panel.add(new BalloonButton(t));
			BalloonButton balloon = new BalloonButton(game, t, panel);
			//balloon.addMouseMotionListener(new MouseMotionHandler());
			balloon.addMouseListener(new MouseHandler());
			panel.add(balloon);
		}
        contentPane.add(panel);
	}
	
	static final long serialVersionUID = new Random().nextInt(50000);
}
