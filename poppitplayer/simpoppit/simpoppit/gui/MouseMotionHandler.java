package simpoppit.gui;
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
        if (event.getSource() instanceof BalloonButton){
            ((BalloonButton)event.getSource()).highlight(true);
        }
	}
	
	public void mouseDragged(MouseEvent event){
//        if (event.getSource() instanceof BalloonButton){
//            ((BalloonButton)event.getSource()).pop();
//        }
	}
}
