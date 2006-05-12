package simpoppit.gameboard;

import java.util.*;
import java.io.Serializable;

/**
 * <p>
 * Title: Coord
 * </p>
 * 
 * <p>
 * Description: This class encapsulates data and methods to allow representation
 * and manipulation of 2-D coordinates.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Andrew W. Henry
 * @version 1.0
 */
public class Coord implements Cloneable, Comparable, Serializable {

    /**
     * Generate a new coordinate object with the given X and Y values
     * @param aX the X coordinate of this point
     * @param aY the Y coordinate of this point
     */
    public Coord(int aX, int aY) {
        this.x = aX;
        this.y = aY;
    }

    /**
     * Return the X coordinate of this Coord object.
     * 
     * @return this object's integer X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Return the Y coordinate of this Coord object.
     * 
     * @return this object's integer Y coordinate
     */
    public int getY() {
        return y;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object aPoint) {
        if ((aPoint instanceof Coord)
                && (((Coord) aPoint).getX() == this.x & ((Coord) aPoint).getY() == this.y))
            return true;
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable
     */
    public int compareTo(Object aPoint) {
        int result = 0;
        result = this.x - ((Coord) aPoint).getX();
        if (result == 0) {
            result = this.y - ((Coord) aPoint).getY();
        }
        return result;
    }

    public Object clone() {
        Coord result = new Coord(this.x, this.y);
        return result;
    }

    public String toString() {
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
    
    static final long serialVersionUID = 1234;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Coord test1 = new Coord(3, 3);
        Coord test2 = new Coord(3, 3);
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
