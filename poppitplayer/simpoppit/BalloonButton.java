import java.util.Random;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * 
 */

/**
 * @author Andrew W. Henry
 *
 */

public class BalloonButton extends JComponent{
	
	public BalloonButton(Coord aCoord){
		this.label = aCoord.toString();
		this.color = null;
		
	}
	
	public BalloonButton(Balloon.Color aColor){
		this.label = aColor.toString();
		this.color = null;
		switch (aColor){
			case BLUE: this.color = Color.BLUE; break;
			case GREEN: this.color = Color.GREEN; break;
			case PURPLE: this.color = Color.MAGENTA; break;
			case RED: this.color = Color.RED; break;
			case YELLOW: this.color = Color.YELLOW; break;
			case EMPTY: this.color = Color.WHITE; break;
		}
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
		
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f = new Font("SanSerif", Font.PLAIN, 8);
		g2.setFont(f);
		Rectangle2D bounds = f.getStringBounds(this.label, context);
		double x = (getWidth() - bounds.getWidth()) / 2;
		double y = (getHeight() - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;
		g.drawString(this.label, (int)x, (int)baseY);
	}
	
	private final String label;
	private Color color;
	static final long serialVersionUID = new Random().nextInt(50000);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
