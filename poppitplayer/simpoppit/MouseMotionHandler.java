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
		System.out.println("Mouse moved.");
	}
	
	public void mouseDragged(MouseEvent event){
		
	}
	
	public void mouseEntered(MouseEvent event){
		System.out.println("Mouse entered.");
	}
	
	public void mouseExited(MouseEvent event){
		System.out.println("Mouse exited.");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
