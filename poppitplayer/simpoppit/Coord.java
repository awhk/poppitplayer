/**
 * <p>Title: Coord</p>
 *
 * <p>Description: Coordinate class</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Andrew W. Henry
 * @version 1.0
 */
public class Coord {
    
    public Coord(int aX, int aY){
        this.x = aX;
        this.y = aY;
    }
    
    public int GetX(){
        return x;
    }
    
    public int GetY(){
        return y;
    }

    
    private int x;
    private int y;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
