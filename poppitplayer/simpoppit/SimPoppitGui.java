import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * 
 */

/**
 * @author Andrew W. Henry
 *
 */
public class SimPoppitGui extends JFrame{
	
	public SimPoppitGui(){
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		
		setSize(screensize.width/2, screensize.height/2);
		setLocation(screensize.width/4, screensize.height/4);
		
		setTitle("SimPoppit");
		
		Image img = kit.getImage("icon.png");
		setIconImage(img);
        
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
        Container contentPane = getContentPane();
        contentPane.add(panel);
	}

	static final long serialVersionUID = new Random().nextInt(50000);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimPoppitGui frame = new SimPoppitGui();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.show();
	}

}

class PoppitPanel extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
    static final long serialVersionUID = new Random().nextInt(50000);
}
