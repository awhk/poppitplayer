import java.awt.event.*;
/**
 * 
 */

/**
 * @author datacomm
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
