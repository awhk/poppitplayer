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

public class BalloonButton extends JComponent implements GameListener{
	
	public BalloonButton(GameInterface aGame, Coord aCoord){
		this.game = aGame;
		this.coord = aCoord;
		this.label = game.getBalloon(this.coord).toString();
		this.color = null;
		this.highlight = false;
		this.color = this.convertColor(game.getBalloon(this.coord).color());
	}
    
    public void gameEventReceived(GameEvent event){
        if (!event.getCoords().contains(this.coord)) return;
        if (event.getAction() == "update"){
            this.color = this.convertColor(this.game.getBalloon(this.coord).color());
        }
        if (event.getAction() == "pop"){
            this.highlight = false;
            this.color = this.convertColor(this.game.getBalloon(this.coord).color());
        }
        if (event.getAction() == "highlight"){
            this.highlight = true;
        }
        if (event.getAction() == "unhighlight"){
            this.highlight = false;
        }
        repaint();
    }
	
	public void highlight(boolean aHighlight){
        if (aHighlight & this.highlight) return;
        if (!aHighlight & !this.highlight) return;
        if (aHighlight){
            this.game.highlight(this.coord);
        }else{
            this.game.unHighlight(this.coord);
        }
		repaint();
	}
	
	
	public Coord getCoord(){
		return this.coord;
	}
	
    protected void pop(){
        this.game.pop(this.coord);
    }
    
    protected void popAll(){
        this.game.popAll();
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
        if (this.highlight) {
            //System.out.println("Highlighting button " + this.coord);
            int red = g.getColor().getRed()+175;
            int green = g.getColor().getGreen()+175;
            int blue = g.getColor().getBlue()+175;
            if (red > 255) red = 255;
            if (green > 255) green = 255;
            if (blue > 255) blue = 255;
            g.setColor(new Color(red, green, blue));
            //g.setColor(Color.LIGHT_GRAY);
            //System.out.println("Highlighting " + this.coord + " to color " + g.getColor());
        }
		g.fillOval(0, 0, getWidth(), getHeight());
        if (this.highlight){
            g.setColor(Color.GRAY);
            g.drawOval(0, 0, getWidth(), getHeight());
            g.drawOval(1, 1, getWidth()-2, getHeight()-2);
        }
		
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
		
	}
    
    private Color convertColor(Balloon.Color aColor){
        Color result;
        switch (aColor){
        case BLUE: result = Color.BLUE; break;
        case GREEN: result = Color.GREEN; break;
        case PURPLE: result = Color.MAGENTA; break;
        case RED: result = Color.RED; break;
        case YELLOW: result = Color.YELLOW; break;
        case EMPTY: result = Color.WHITE; break;
        default: result = Color.LIGHT_GRAY;
        }
        return result;
    }
	
	private final String label;
	private final Coord coord;
	private Color color;
	private boolean highlight;
	private GameInterface game;
	static final long serialVersionUID = new Random().nextInt(50000);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
