/**
 * <p>Title: GameGrid</p>
 *
 * <p>Description: Game grid class</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Andrew W. Henry
 * @version 1.0
 */

import java.util.*;

public class GameGrid {

    public GameGrid(){
        this(15);
    }
    public GameGrid(int aNumberOfColumns){
        this(aNumberOfColumns, 10);
    }
    
    public GameGrid(int aNumberOfColumns, int aNumberOfRows){
        if ((aNumberOfColumns % 2) == 0){
            aNumberOfColumns++;
            System.out.println("Number of columns must be odd.  Incremented.");
        }
        this.grid = new BalloonColumn[aNumberOfColumns];
        for (int i=0; i<this.Columns(); i++){
            this.grid[i] = new BalloonColumn(aNumberOfRows);
        }
        this.xMax = aNumberOfColumns-1;
        this.yMax = aNumberOfRows-1;
        this.bottomRight = new Coord(aNumberOfColumns-1, aNumberOfRows-1);
    }
    
    public int Columns(){
        return grid.length;
    }
    
    public Coord GridSize(){
        return this.bottomRight;
    }
    
    public Balloon.Color Color(int aX, int aY){
        return this.Color(new Coord(aX, aY));
    }
    
    public Balloon.Color Color(Coord aBalloon){
        if (aBalloon.GetX() > this.xMax || aBalloon.GetY() > this.yMax){
            return null;
        }
        return this.grid[aBalloon.GetX()].Color(aBalloon.GetY());
    }
    
    public ArrayList<Coord> Neighbors(Coord aBalloon){
        ArrayList<Coord> result = new ArrayList<Coord>();
        for (int i=-1; i<=1; i++){
            for (int j=-1; j<=1; j++){
                Coord myTestPoint = new Coord(aBalloon.GetX()+i, aBalloon.GetY()+j);
                if (bottomRight.isBeyond(myTestPoint) && !(i == j) && !(aBalloon.isDiagonalTo(myTestPoint)) ){
                    result.add(new Coord(aBalloon.GetX()+i, aBalloon.GetY()+j));
                }
            }
        }
        return result;
    }
    
    public ArrayList<Coord> likeColoredNeighbors(Coord aBalloon){
        ArrayList<Coord> result = new ArrayList<Coord>();
        for (int i=-1; i<=1; i++){
            for (int j=-1; j<=1; j++){
                Coord myTestPoint = new Coord(aBalloon.GetX()+i, aBalloon.GetY()+j);
                if (bottomRight.isBeyond(myTestPoint) && !(i == j) && !(aBalloon.isDiagonalTo(myTestPoint)) && (this.Color(myTestPoint) == this.Color(aBalloon)) ){
                    result.add(new Coord(aBalloon.GetX()+i, aBalloon.GetY()+j));
                }
            }
        }
        return result;
    }
    
    public ArrayList<Coord> likeColoredNeighborChain(Coord aBalloon){
        ArrayList<Coord> result = new ArrayList<Coord>();
        ArrayList<Coord> AlreadyVisited = new ArrayList<Coord>();
        Stack<Coord> ToBeTested = new Stack<Coord>();
        ArrayList<Coord> Temp = new ArrayList<Coord>();
        Coord TestCoord = new Coord();
                AlreadyVisited.add(aBalloon);
        ToBeTested.push(aBalloon);
        while (!(ToBeTested.isEmpty())){
            Temp = this.likeColoredNeighbors(ToBeTested.pop());
            boolean AlreadyVisitedFlag = false;
            for (Coord t : Temp){
                //if (AlreadyVisited.contains(t)) continue;
                for (Coord r : AlreadyVisited){
                    if (t.Equals(r)) AlreadyVisitedFlag = true;
                }
                if (!AlreadyVisitedFlag){
                    ToBeTested.push(t);
                    AlreadyVisited.add(t);
                    result.add(t);
                }
             }
        }
        return result;
    }
    
    public String toString(){
        String result = "";
        for (int i=0; i<=this.xMax; i++){
            for (int j=0; j<=this.yMax; j++){
                result += this.grid[j].Color(i);
                result += "\t";
            }
            result += "\n";
        }
        return result;
    }
    
    private int CenterColumn(){
        int length = this.Columns();
        if (length == 1) return 0;
        return ((length-1)/2);
    }
    
    private BalloonColumn[] grid;
    private int xMax;
    private int yMax;
    private Coord bottomRight;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        GameGrid test = new GameGrid(9,9);
        System.out.println(test);
        System.out.print("Center column is ");
        System.out.println(test.CenterColumn());
        System.out.print("Ballon at 2,2 is ");
        System.out.println(test.Color(2,2));
        System.out.print("Ballon at bottom right is ");
        System.out.println(test.Color(test.GridSize()));
        System.out.println("Neighboring balloons to (3,3) are:");
        for (Coord t : test.Neighbors(new Coord(3,3))){
            System.out.println(t);
        }
        System.out.println("Like-colored neighboring balloons to (3,3) are:");
        for (Coord t : test.likeColoredNeighbors(new Coord(3,3))){
            System.out.println(t);
        }
        System.out.println("Like-colored neighbor chain for (3,3) is:");
        for (Coord t : test.likeColoredNeighborChain(new Coord(3,3))){
            System.out.println(t);
        }
    }

}
