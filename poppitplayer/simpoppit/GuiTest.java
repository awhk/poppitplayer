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
public class GuiTest extends JFrame{
	
	public GuiTest(){
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		
		setSize(screensize.width/2, screensize.height/2);
		setLocation(screensize.width/4, screensize.height/4);
		
		setTitle("GUI Test for SimPoppit");
		
		Image img = kit.getImage("icon.png");
		setIconImage(img);
        
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
        Container contentPane = getContentPane();
        contentPane.add(panel);
	}

	static final long serialVersionUID = new Random().nextInt(50000);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GuiTest frame = new GuiTest();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.show();
	}

}

class GuiTestPanel extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        this.yMaxWindow = getHeight();
        this.xMaxWindow = getWidth();
        
//        int balloonHeight = this.yMaxWindow/(this.yMaxGame+1);
//        int balloonWidth = this.xMaxWindow/(this.xMaxGame+1);
//        int startX = balloonWidth/2;
//        int startY = balloonHeight/2;
//        
//        for (BalloonColumn t : game.getColumns()){
//            for (Balloon v : t.getBalloons()){
//                switch (v.color()){
//                case BLUE: g.setColor(Color.BLUE); break;
//                case GREEN: g.setColor(Color.GREEN); break;
//                case PURPLE: g.setColor(Color.MAGENTA); break;
//                case RED: g.setColor(Color.RED); break;
//                case YELLOW: g.setColor(Color.YELLOW); break;
//                case EMPTY: g.setColor(Color.WHITE); break;
//                }
//                g.fillOval(startX-(balloonWidth/2), startY-(balloonHeight/2), balloonWidth, balloonHeight);
//                startY += balloonHeight;
//                g.setColor(Color.BLACK);
//            }
//            startX += balloonWidth;
//            startY = balloonHeight/2;
//        }
//        this.setLayout(new GridLayout(this.xMaxGame, this.yMaxGame));
//		for (Coord t : game.getGridAsList()){
//			this.add(new JButton(t.toString()));
//		}
		
        //System.out.print(game);
        //System.out.println(getWidth());
        //System.out.println(getHeight());
        //System.out.println(balloonWidth);
        //System.out.println(balloonHeight);
    }
    
    public void GameVars(GameGrid aGame){
        this.game = aGame;
        this.xMaxGame = game.gridSize().getX();
        this.yMaxGame = game.gridSize().getY();
    }
	
	public void highlightNeighbors(Coord aCoord){
		
		this.coord = aCoord;
		
		for (Component t : this.getComponents()){
			BalloonButton b = (BalloonButton)t;
			if (game.likeColoredNeighborChain(this.coord).contains(b.getCoord()) | b.getCoord() == this.coord){
				//System.out.print("Highlighting ");
				//System.out.println(b.getCoord());
				b.simpleHighlight(true);
			}else{
				//System.out.print("Unhighlighting ");
				//System.out.println(b.getCoord());
				b.simpleHighlight(false);
			}
		}
	}
	
	public void repaintGame(){
		for (Component t : this.getComponents()){
			BalloonButton b = (BalloonButton)t;
			b.simpleHighlight(false);
			b.setColor(game.color(b.getCoord()));
		}
		repaint();
		//System.out.println(game);
	}
	
	public int getGameX(){
		return this.xMaxGame;
	}
	
	public int getGameY(){
		return this.yMaxGame;
	}
    
	private Coord coord;
    private int xMaxWindow;
    private int yMaxWindow;
    private int xMaxGame;
    private int yMaxGame;
    private GameGrid game;
    static final long serialVersionUID = new Random().nextInt(50000);
}
