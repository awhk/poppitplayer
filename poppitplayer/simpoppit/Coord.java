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
    
    public Coord(){
        this(0,0);
    }
    
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
    
    public boolean isBeyond(Coord aPoint){
        if (aPoint.GetX() > this.x) return false;
        if (aPoint.GetY() > this.y) return false;
        return true;
    }
    
    public boolean Equals(Coord aPoint){
        if (aPoint.GetX() == this.x & aPoint.GetY() == this.y) return true;
        return false;
    }
    
    public boolean isDiagonalTo(Coord aPoint){
        if (aPoint.GetX() == this.x-1 & aPoint.GetY() == this.y-1) return true;
        if (aPoint.GetX() == this.x+1 & aPoint.GetY() == this.y-1) return true;
        if (aPoint.GetX() == this.x-1 & aPoint.GetY() == this.y+1) return true;
        if (aPoint.GetX() == this.x+1 & aPoint.GetY() == this.y+1) return true;
        return false;
    }
    
    public String toString(){
        String result = "";
        result += "(";
        result += this.x;
        result += ",";
        result += this.y;
        result += ")";
        return result;
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
