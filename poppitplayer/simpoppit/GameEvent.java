import java.util.*;
/**
 * 
 */

/**
 * @author Andrew
 *
 */
public class GameEvent extends EventObject {

    public GameEvent(Object source, ArrayList<Coord> someCoords, String anAction){
        super(source);
        coords = someCoords;
        action = anAction;
    }
    
    public ArrayList<Coord> getCoords(){
        return this.coords;
    }
    
    public String getAction(){
        return this.action;
    }
    
    private ArrayList<Coord> coords;
    private String action;
    static final long serialVersionUID = new Random().nextInt(50000);
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}