import java.awt.event.*;
/**
 * 
 */

/**
 * @author Andrew W. Henry
 *
 */
public class MouseMotionHandler implements MouseMotionListener{

	public void mouseMoved(MouseEvent event){
        Object b = event.getComponent();
        BalloonButton c = (BalloonButton) b;
        c.highlight(true);
	}
	
	public void mouseDragged(MouseEvent event){
		
	}
}
