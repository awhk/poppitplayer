import java.util.*;
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
public class Coord implements Cloneable{
    
    public Coord(){
        this(0,0);
    }
    
    public Coord(int aX, int aY){
        this.x = aX;
        this.y = aY;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean isBeyond(Coord aPoint){
        if (aPoint.getX() > this.x) return false;
        if (aPoint.getY() > this.y) return false;
        return true;
    }
    
    public boolean equals(Object aPoint){
        if ((aPoint instanceof Coord) && (((Coord)aPoint).getX() == this.x & ((Coord)aPoint).getY() == this.y) ) return true;
        return false;
    }
    
    public Object clone(){
        Coord result = new Coord(this.x, this.y);
        return (Object)result;
    }
    
    public boolean isDiagonalTo(Coord aPoint){
        if (aPoint.getX() == this.x-1 & aPoint.getY() == this.y-1) return true;
        if (aPoint.getX() == this.x+1 & aPoint.getY() == this.y-1) return true;
        if (aPoint.getX() == this.x-1 & aPoint.getY() == this.y+1) return true;
        if (aPoint.getX() == this.x+1 & aPoint.getY() == this.y+1) return true;
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
        Coord test1 = new Coord(3,3);
        Coord test2 = new Coord(3,3);
        ArrayList<Coord> testlist = new ArrayList<Coord>();
        testlist.add(test1);
        System.out.print("test1 = test2 is ");
        System.out.println(test1.equals(test2));
        System.out.print("testlist contains test2 is ");
        System.out.println(testlist.contains(test2));
        System.out.print("null == null is ");
        System.out.println(null == null);

    }

}
