import java.util.*;
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
        for (int i=0; i<this.columns(); i++){
            this.grid[i] = new BalloonColumn(aNumberOfRows);
        }
        this.xMax = aNumberOfColumns-1;
        this.yMax = aNumberOfRows-1;
        this.bottomRight = new Coord(aNumberOfColumns-1, aNumberOfRows-1);
    }
    
    public int columns(){
        return grid.length;
    }
    
    public Coord gridSize(){
        return this.bottomRight;
    }
    
    public Balloon.Color color(int aX, int aY){
        return this.color(new Coord(aX, aY));
    }
    
    public Balloon.Color color(Coord aBalloon){
        if (aBalloon.getX() > this.xMax || aBalloon.getY() > this.yMax){
            return null;
        }
        return this.grid[aBalloon.getX()].color(aBalloon.getY());
    }
    
    public ArrayList<Coord> neighbors(Coord aBalloon){
        ArrayList<Coord> result = new ArrayList<Coord>();
        for (int i=-1; i<=1; i++){
            for (int j=-1; j<=1; j++){
                Coord myTestPoint = new Coord(aBalloon.getX()+i, aBalloon.getY()+j);
                if (bottomRight.isBeyond(myTestPoint) && !(i == j) && !(aBalloon.isDiagonalTo(myTestPoint)) ){
                    result.add(new Coord(aBalloon.getX()+i, aBalloon.getY()+j));
                }
            }
        }
        return result;
    }
    
    public ArrayList<Coord> likeColoredNeighbors(Coord aBalloon){
        ArrayList<Coord> result = new ArrayList<Coord>();
        for (int i=-1; i<=1; i++){
            for (int j=-1; j<=1; j++){
                Coord myTestPoint = new Coord(aBalloon.getX()+i, aBalloon.getY()+j);
                if ( myTestPoint.getX()>=0 && myTestPoint.getY()>=0 && bottomRight.isBeyond(myTestPoint) && !(i == j) && !(aBalloon.isDiagonalTo(myTestPoint)) && (this.color(myTestPoint) == this.color(aBalloon)) ){
                    result.add(new Coord(aBalloon.getX()+i, aBalloon.getY()+j));
                }
            }
        }
        return result;
    }
    
    public ArrayList<Coord> likeColoredNeighborChain(Coord aBalloon){
        ArrayList<Coord> result = new ArrayList<Coord>();
        ArrayList<Coord> alreadyVisited = new ArrayList<Coord>();
        Stack<Coord> toBeTested = new Stack<Coord>();
        ArrayList<Coord> temp = new ArrayList<Coord>();
        Coord testCoord = new Coord();
                alreadyVisited.add(aBalloon);
        toBeTested.push(aBalloon);
        while (!(toBeTested.isEmpty())){
            temp = this.likeColoredNeighbors(toBeTested.pop());
            for (Coord t : temp){
                if (alreadyVisited.contains(t)) continue;
                toBeTested.push(t);
                alreadyVisited.add(t);
                result.add(t);
             }
        }
        return result;
    }
    
    public void pop(Coord aBalloon){
        this.grid[aBalloon.getX()].pop(aBalloon.getY());
    }
    
    public void popChain(ArrayList<Coord> balloonList){
        for (Coord t : balloonList){
            this.pop(t);
        }
    }    
        
    public void squeezeAll(){
        this.squeezeColumns();
        this.squeezeRows();
    }
    
    public String toString(){
        String result = "";
        for (int i=0; i<=this.yMax; i++){
            for (int j=0; j<=this.xMax; j++){
                result += this.grid[j].color(i);
                result += "\t";
            }
            result += "\n";
        }
        return result;
    }
    
    private int centerColumn(){
        int length = this.columns();
        if (length == 1) return 0;
        return ((length-1)/2);
    }
    
    private void squeezeColumns(){
        for (BalloonColumn t : this.grid){
            t.squeeze();
        }
    }
    
    private void squeezeRows(){
        ArrayList<BalloonColumn> myGrid = new ArrayList<BalloonColumn>();
        boolean swapped = false;
        for (int i=0; i<this.columns(); i++){
            myGrid.add(i, this.grid[i]);
        }
        for (int i=0; i<this.columns(); i++){
            if (myGrid.get(i).isEmpty()){
                if (i == 0 || i == this.xMax || i == this.centerColumn()) continue;
                if (i < this.centerColumn()){
                    Collections.swap(myGrid, i-1, i);
                    swapped = true;
                }else{
                    Collections.swap(myGrid, i+1, i);
                    swapped = true;
                }
            }
        }
        //this.grid = myGrid.toArray(BalloonColumn.class);
        for (int i=0; i<this.columns(); i++){
            this.grid[i] = myGrid.get(i);
        }
        if (swapped) squeezeRows();
    }
    
    private BalloonColumn[] grid;
    private int xMax;
    private int yMax;
    private Coord bottomRight;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        GameGrid test = new GameGrid();
        System.out.println(test);
        System.out.print("Center column is ");
        System.out.println(test.centerColumn());
        //System.out.print("Ballon at 2,2 is ");
        //System.out.println(test.color(2,2));
        //System.out.print("Ballon at bottom right is ");
        //System.out.println(test.color(test.gridSize()));
        //System.out.println("Neighboring balloons to (3,3) are:");
        //for (Coord t : test.neighbors(new Coord(3,3))){
        //    System.out.println(t);
        //}
        System.out.println("Like-colored neighboring balloons to (1,1) are:");
        for (Coord t : test.likeColoredNeighbors(new Coord(1,1))){
            System.out.println(t);
        }
        System.out.println("Like-colored neighbor chain for (1,1) is:");
        for (Coord t : test.likeColoredNeighborChain(new Coord(1,1))){
            System.out.println(t);
        }
        System.out.println("Popping the like-colored neighbor chain for (1,1)...");
        test.popChain(test.likeColoredNeighborChain(new Coord(1,1)));
        test.pop(new Coord(1,1));
        System.out.println(test);
        System.out.println("Squeezing columns...");
        test.squeezeColumns();
        System.out.println(test);
        System.out.println("Popping column 9...");
        for (int i=0; i<=test.yMax; i++){
            test.pop(new Coord(9,i));
        }
        System.out.println(test);
        System.out.println("Squeezing rows...");
        test.squeezeRows();
        System.out.println(test);
    }

}
