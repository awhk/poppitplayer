/**
 * <p>Title: Balloon</p>
 *
 * <p>Description: Balloon class</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Andrew W. Henry
 * @version 1.0
 */
import java.util.*;
public class Balloon {
    public Balloon() {
        ArrayList<Color> temp = new ArrayList<Color>();
        temp.addAll(Arrays.asList(Color.values()));
        temp.remove(Color.EMPTY);
        Collections.shuffle(temp, new Random());
        this.color = (Color) temp.get(0);
    }

    public Color color() { return color; }

    public String toString() { return color + ""; }

    public boolean isPopped(){
        if (color == Color.EMPTY){
            return true;
        }
        return false;
    }
    
    public void Pop(){
        this.color = Color.EMPTY;
    }

    private Balloon(Color aColor){
        this.color = aColor;
    }

    public static enum Color { BLUE, RED, YELLOW, PURPLE, GREEN, EMPTY }
    private Color color;
    
    public static void main(String[] args) {
        Balloon test = new Balloon();
        System.out.println( "My balloon is " + test.color());
        System.out.println( "Popping balloon...");
        test.Pop();
        if(test.isPopped()){
            System.out.println("My balloon is popped!");
        }else{
            System.out.println("My balloon is NOT popped...oops!");
        }
    }
}
