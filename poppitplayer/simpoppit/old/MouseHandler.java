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
        if (event.getSource() instanceof BalloonButton){
            ((BalloonButton)event.getSource()).highlight(true);
        }
	}
	
	public void mouseExited(MouseEvent event){
        if (event.getSource() instanceof BalloonButton){
            ((BalloonButton)event.getSource()).highlight(false);
        }
	}
	
    public void mouseClicked(MouseEvent event){
        if (event.getSource() instanceof BalloonButton){
            if (event.getModifiersEx() == InputEvent.CTRL_DOWN_MASK){
                ((BalloonButton)event.getSource()).popAll();
            }else{
                ((BalloonButton)event.getSource()).pop();
            }
        }
    }
}
