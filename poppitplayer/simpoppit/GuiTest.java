import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
		
		Image img = kit.getImage("icon.gif");
		setIconImage(img);
	}

	static final long serialVersionUID = 1234;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GuiTest frame = new GuiTest();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setVisible(true);
		frame.show();
	}

}
