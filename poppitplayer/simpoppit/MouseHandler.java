import java.awt.event.*;
/**
 * 
 */

/**
 * @author Andrew W. Henry
 *
 */
public class MouseHandler extends MouseAdapter{

	public void mouseEntered(MouseEvent event){
		Object b = event.getComponent();
		BalloonButton c = (BalloonButton) b;
		c.highlight(true);
		//System.out.print("Mouse entered ");
		//System.out.println(event.getComponent());
	}
	
	public void mouseExited(MouseEvent event){
		Object b = event.getComponent();
		BalloonButton c = (BalloonButton) b;
		c.highlight(false);
		//System.out.println("Mouse exited.");
	}
	
	public void mouseClicked(MouseEvent event){
		Object b = event.getComponent();
		BalloonButton c = (BalloonButton) b;
		c.pop();
	}
}
