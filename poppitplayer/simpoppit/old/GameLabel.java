import java.util.Random;
import javax.swing.*;
/**
 * 
 */

/**
 * @author Andrew W. Henry
 *
 */
public class GameLabel extends JLabel implements GameListener {

    public GameLabel(String aLabel, String aType){
        super(aLabel);
        this.type = aType;
    }
    
    public String getType(){
        return this.type;
    }
    
    public void gameEventReceived(GameEvent event){
        if (this.type == "score"){
            if (event.getAction() == "update"){
                this.setText(((GameInterface)event.getSource()).getScore() + "");
            }
            if (event.getAction() == "gameover"){
                this.setText(((GameInterface)event.getSource()).getScore() + " FINAL SCORE (no moves remain)");
            }
        }
        if (this.type == "value"){
            if (event.getAction() == "highlight"){
                this.setText(((GameInterface)event.getSource()).valueOfMove(event.getCoords().get(0)) + "");
            }
            if (event.getAction() == "unhighlight"){
                this.setText("");
            }
        }
        
    }
    
    private String type;
    static final long serialVersionUID = new Random().nextInt(50000);
}
