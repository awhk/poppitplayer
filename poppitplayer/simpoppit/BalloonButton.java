import java.util.Random;
import java.awt.*;
//import java.awt.font.*;
//import java.awt.geom.*;
import javax.swing.*;

/**
 * 
 */

/**
 * @author Andrew W. Henry
 *
 */

public class BalloonButton extends JComponent{
	
	public BalloonButton(GameGrid aGame, Coord aCoord, JPanel aPanel){
		this.game = aGame;
		this.coord = aCoord;
		this.parentPanel = aPanel;
		this.label = game.color(this.coord).toString();
		this.color = null;
		this.highlight = false;
		switch (game.color(this.coord)){
			case BLUE: this.color = Color.BLUE; break;
			case GREEN: this.color = Color.GREEN; break;
			case PURPLE: this.color = Color.MAGENTA; break;
			case RED: this.color = Color.RED; break;
			case YELLOW: this.color = Color.YELLOW; break;
			case EMPTY: this.color = Color.WHITE; break;
		}
	}
	
	public void highlight(boolean aHighlight){
		this.highlight = aHighlight;
		GuiTestPanel p = (GuiTestPanel)this.parentPanel;
		p.highlightNeighbors(this.coord);
		repaint();
	}
	
	public void simpleHighlight(boolean aHighlight){
		this.highlight = aHighlight;
		repaint();
	}
	
	public boolean isHighlighted(){
		return this.highlight;
	}
	
	public Coord getCoord(){
		return this.coord;
	}
	
	public void setColor(Balloon.Color aColor){
		switch (aColor){
		case BLUE: this.color = Color.BLUE; break;
		case GREEN: this.color = Color.GREEN; break;
		case PURPLE: this.color = Color.MAGENTA; break;
		case RED: this.color = Color.RED; break;
		case YELLOW: this.color = Color.YELLOW; break;
		case EMPTY: this.color = Color.WHITE; break;
		}
		repaint();
	}
	
	public void pop(){
		GuiTestPanel p = (GuiTestPanel)this.parentPanel;
		if (!(game.hasLikeColoredNeighbors(this.coord))) return;
		game.popChain(game.likeColoredNeighborChain(this.coord));
		game.pop(this.coord);
		//p.repaintGame();
//		try{
//			Thread.sleep(1000);
//		}
//		catch (Exception e){
//			System.out.println("Couldn't sleep!");
//		}
		game.squeezeAll();
		p.repaintGame();
	}

	protected void paintComponent(Graphics g){
		if (isOpaque()){
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
		
		if (this.color == null){
			this.color = Color.LIGHT_GRAY;
		}

		g.setColor(this.color);
		g.fillOval(0, 0, getWidth(), getHeight());
		
//		g.setColor(Color.BLACK);
//		Graphics2D g2 = (Graphics2D)g;
//		FontRenderContext context = g2.getFontRenderContext();
//		Font f = new Font("SanSerif", Font.PLAIN, 8);
//		g2.setFont(f);
//		Rectangle2D bounds = f.getStringBounds(this.label, context);
//		double x = (getWidth() - bounds.getWidth()) / 2;
//		double y = (getHeight() - bounds.getHeight()) / 2;
//		double ascent = -bounds.getY();
//		double baseY = y + ascent;
//		g.drawString(this.label, (int)x, (int)baseY);
		
		if (this.highlight & this.game.hasLikeColoredNeighbors().contains(this.coord)){
			g.setColor(Color.BLACK);
			g.drawOval(1, 1, getWidth()-3, getHeight()-3);
			g.drawOval(0, 0, getWidth()-1, getHeight()-1);
		}
	}
	
	private final String label;
	private final Coord coord;
	private final JPanel parentPanel;
	private Color color;
	private boolean highlight;
	private GameGrid game;
	static final long serialVersionUID = new Random().nextInt(50000);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
