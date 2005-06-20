package simpoppit.gameboard;
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
public class Coord implements Cloneable, Comparable{
    
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
    
    public boolean equals(Object aPoint){
        if ((aPoint instanceof Coord) && (((Coord)aPoint).getX() == this.x & ((Coord)aPoint).getY() == this.y) ) return true;
        return false;
    }
    
    public int compareTo(Object aPoint){
        int result = 0;
        result = this.x - ((Coord)aPoint).getX();
        if (result == 0){
                result = this.y - ((Coord)aPoint).getY();
        }
        return result;
    }
    
    public Object clone(){
        Coord result = new Coord(this.x, this.y);
        return result;
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
