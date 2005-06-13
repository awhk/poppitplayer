package simpoppit.gameboard;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

//import org.apache.log4j.Logger;

/**
 * <p>
 * Title: GameGrid
 * </p>
 * 
 * <p>
 * Description: Game grid class
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Andrew W. Henry
 * @version 1.0
 */

public class GameGrid implements Cloneable, Comparable, GameConsts {

    public GameGrid() {
        this(15);
    }

    public GameGrid(int aNumberOfColumns) {
        this(aNumberOfColumns, 10);
    }

    public GameGrid(int aNumberOfColumns, int aNumberOfRows) {
//        if ((aNumberOfColumns % 2) == 0) {
//            aNumberOfColumns++;
//            System.out.println("Number of columns must be odd.  Incremented.");
//        }
        GameGrid.xMax = aNumberOfColumns - 1;
        GameGrid.yMax = aNumberOfRows - 1;
        this.grid = new byte[aNumberOfColumns * aNumberOfRows];
        for (int y=0; y<=GameGrid.yMax; y++) {
            for (int x=0; x<=GameGrid.xMax; x++){
                this.grid[(x * GameGrid.getHeight()) + y] = randColor();
            }
        }
        //this.bottomRight = new Coord(aNumberOfColumns - 1, aNumberOfRows - 1);
    }
    
    private static byte randColor(){
		Random rand = new Random();
        int color = rand.nextInt(COLORS.length - 1) + 1;
        return COLORS[color];
    }

    public int getSize() {
        //return GameGrid.xMax * GameGrid.yMax;
        return grid.length;
    }
    
    private static int getHeight(){
        return GameGrid.yMax + 1;
    }

    public Coord gridSize() {
        return new Coord(GameGrid.xMax, GameGrid.yMax);
    }

    public byte getColor(int aX, int aY) {
        return this.grid[(aX * GameGrid.getHeight()) + aY];
    }

    public byte getColor(Coord aBalloon) {
        return this.getColor(aBalloon.getX(), aBalloon.getY());
    }
    
    public String getColorAsString(Coord aBalloon){
        byte color = getColor(aBalloon);
        String result = "UNKNOWN";
        switch (color){
        case EMPTY:  result = "EMPTY";  break;
        case RED:    result = "RED";    break;
        case YELLOW: result = "YELLOW"; break;
        case PURPLE: result = "PURPLE"; break;
        case GREEN:  result = "GREEN";  break;
        case BLUE:   result = "BLUE";   break;
        }
        return result;
    }
    
    public boolean isPopped(Coord aBalloon){
        return (this.grid[(aBalloon.getX() * GameGrid.getHeight()) + aBalloon.getY()] == EMPTY);
    }

    public ArrayList<Coord> neighbors(Coord aBalloon) {
        ArrayList<Coord> result = new ArrayList<Coord>(4);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coord myTestPoint = new Coord(aBalloon.getX() + i, aBalloon
                        .getY()
                        + j);
                if ((new Coord(GameGrid.xMax, GameGrid.yMax)).isBeyond(myTestPoint) && !(i == j)
                        && !(aBalloon.isDiagonalTo(myTestPoint))) {
                    result.add(new Coord(aBalloon.getX() + i, aBalloon.getY()
                            + j));
                }
            }
        }
        return result;
    }

    public ArrayList<Coord> likeColoredNeighbors(Coord aBalloon) {
        ArrayList<Coord> result = new ArrayList<Coord>(4);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coord myTestPoint = new Coord(aBalloon.getX() + i, aBalloon
                        .getY()
                        + j);
                if (myTestPoint.getX() >= 0
                        && myTestPoint.getY() >= 0
                        && (new Coord(GameGrid.xMax, GameGrid.yMax)).isBeyond(myTestPoint)
                        && !(i == j)
                        && !(aBalloon.isDiagonalTo(myTestPoint))
                        && !(this.isPopped(myTestPoint))
                        && !(this.isPopped(aBalloon))
                        && (this.getColor(myTestPoint) == (this.getColor(aBalloon)))) {
                    result.add(new Coord(aBalloon.getX() + i, aBalloon.getY()
                            + j));
                }
            }
        }
        return result;
    }

    public ArrayList<Coord> likeColoredNeighborChain(Coord aBalloon) {
        ArrayList<Coord> result = new ArrayList<Coord>();
        //ArrayList<Coord> alreadyVisited = new ArrayList<Coord>();
        Stack<Coord> toBeTested = new Stack<Coord>();
        ArrayList<Coord> temp = new ArrayList<Coord>();
        //alreadyVisited.add(aBalloon);
        toBeTested.push(aBalloon);
        while (!(toBeTested.isEmpty())) {
            temp = this.likeColoredNeighbors(toBeTested.pop());
            for (Coord t : temp) {
                if (result.contains(t))
                    continue;
                toBeTested.push(t);
                //alreadyVisited.add(t);
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Coord> getGridAsList() {
        ArrayList<Coord> result = new ArrayList<Coord>(this.getSize());
        for (int y = 0; y <= GameGrid.yMax; y++) {
            for (int x = 0; x <= GameGrid.xMax; x++) {
                result.add(new Coord(x, y));
            }
        }
        return result;
    }

    public ArrayList<Coord> hasLikeColoredNeighbors() {
        ArrayList<Coord> result = new ArrayList<Coord>();
        for (Coord t : this.getGridAsList()) {
            if (this.hasLikeColoredNeighbors(t))
                result.add(t);
        }
        return result;
    }

    public boolean hasLikeColoredNeighbors(Coord aBalloon) {
        if (this.likeColoredNeighbors(aBalloon).size() > 0)
            return true;
        return false;
    }

    public void pop(Coord aBalloon) {
        this.grid[(aBalloon.getX() * GameGrid.getHeight()) + aBalloon.getY()] = EMPTY;
    }

    public void popChain(ArrayList<Coord> balloonList) {
        for (Coord t : balloonList) {
            this.pop(t);
        }
    }

    public void squeezeAll() {
        this.squeezeColumns();
        this.squeezeRows();
    }

    public String toString() {
        String result = "";
        for (int y=0; y<=GameGrid.yMax; y++){
            for (int x=0; x<=GameGrid.xMax; x++){
                result += this.grid[(x * GameGrid.getHeight()) + y];
                result += "   ";
            }
            result += "\n";
        }
        return result;
    }

    private float centerColumn() {
        return (GameGrid.xMax / 2);
    }


    private void squeezeColumns() {
        //This method squeezes each column vertically
        //if needed.
        for (int x=0; x<=GameGrid.xMax; x++){
            squeezeColumn(x);
        }
    }
    
    private void squeezeColumn(int aColumn){
        boolean sawPopped = false;
        for (int y=0; y<=GameGrid.yMax; y++){
            if (this.isPopped(new Coord(aColumn,y))){
                sawPopped = true;
                continue;
            }
            if (sawPopped && y>0){
                byte temp = grid[(aColumn * GameGrid.getHeight()) + (y-1)];
                grid[(aColumn * GameGrid.getHeight() ) + (y-1)] = grid[(aColumn * GameGrid.getHeight() ) + y];
                grid[(aColumn * GameGrid.getHeight() ) + y] = temp;
                squeezeColumn(aColumn);
                break;
            }
        }
    }
	
	private boolean columnIsEmpty(int aColumn){
		for(int y=0; y<=GameGrid.yMax; y++){
			if (this.grid[(aColumn * GameGrid.getHeight() ) + y] != EMPTY) return false;
		}
		return true;
	}

    private void squeezeRows() {
        //This method squeezes columns together,
        //moving empty columns outward.
        boolean swapped = squeezeRow();
        while(swapped){
            swapped = squeezeRow();
        }
    }
    
    private boolean squeezeRow() {
        boolean swapped = false;

            for (int x = 0; x <= centerColumn(); x++) {
                if (columnIsEmpty(x) && x > 0 && !(columnIsEmpty(x - 1))) {
                    swapped = true;
                    swapColumns(x, x - 1);
                }
            }

        for (int x = GameGrid.xMax; x >= centerColumn(); x--) {
            if (columnIsEmpty(x) && x < GameGrid.xMax && !(columnIsEmpty(x + 1))) {
                swapped = true;
                swapColumns(x, x + 1);
            }
        }
        return swapped;
    }
    
    private void swapColumns(int column1, int column2){
        for (int y=0; y<=GameGrid.yMax; y++){
            byte temp = grid[(column1 * GameGrid.getHeight()) + y];
            grid[(column1 * GameGrid.getHeight()) + y] = grid[(column2 * GameGrid.getHeight()) + y];
            grid[(column2 * GameGrid.getHeight()) + y] = temp;
        }
    }
    
//    private int[] columnCount(){
//        int result[] = {0,0};
//        for (int x=0; x<=GameGrid.xMax; x++){
//            if (columnIsEmpty(x)) continue;
//            if (x < centerColumn()) result[0]++;
//            if (x> centerColumn()) result[1]++;
//        }
//        return result;
//    }

    public boolean equals(Object aGrid) {
        if (!(aGrid instanceof GameGrid))
            return false;
        if (!(((GameGrid) aGrid).getSize() == this.getSize()))
            return false;
		for (int y=0; y<=GameGrid.yMax; y++){
            for (int x=0; x<=GameGrid.xMax; x++){
                if (this.getColor(x,y) != ((GameGrid)aGrid).getColor(x,y)) return false;
            }
        }
        return true;
    }

    public Object clone() {
        GameGrid result = new GameGrid(GameGrid.xMax + 1, GameGrid.yMax + 1);
        for (int y=0; y<=GameGrid.yMax; y++){
            for (int x=0; x<=GameGrid.xMax; x++){
                result.grid[(x * GameGrid.getHeight()) + y] = this.grid[(x * GameGrid.getHeight()) + y];
            }
        }
        return result;
    }
    
    public int compareTo(Object aGrid) {
        int result = 0;
        compare:
        for (int y = 0; y <= GameGrid.yMax; y++) {
            for (int x = 0; x <= GameGrid.xMax; x++) {
                result = grid[(x * GameGrid.getHeight()) + y] - ((GameGrid) aGrid).grid[(x * GameGrid.getHeight()) + y];
                if (result != 0)
                    break compare;
            }
        }
        return result;
    }

    private static int xMax;
    private static int yMax;
    //private Coord bottomRight;
    //private HashMap<Coord, Color> grid;
	private byte[] grid;

    /**
     * @param args
     */
    public static void main(String[] args) {
        GameGrid test = new GameGrid();
        System.out.println(test);
        System.out.print("Center column is ");
        System.out.println(test.centerColumn());
        // System.out.print("Ballon at 2,2 is ");
        // System.out.println(test.color(2,2));
        // System.out.print("Ballon at bottom right is ");
        // System.out.println(test.color(test.gridSize()));
        // System.out.println("Neighboring balloons to (3,3) are:");
        // for (Coord t : test.neighbors(new Coord(3,3))){
        // System.out.println(t);
        // }
        System.out.println("Like-colored neighboring balloons to (1,1) are:");
        for (Coord t : test.likeColoredNeighbors(new Coord(1, 1))) {
            System.out.println(t);
        }
        System.out.println("Like-colored neighbor chain for (1,1) is:");
        for (Coord t : test.likeColoredNeighborChain(new Coord(1, 1))) {
            System.out.println(t);
        }
        System.out
                .println("Popping the like-colored neighbor chain for (1,1)...");
        test.popChain(test.likeColoredNeighborChain(new Coord(1, 1)));
        test.pop(new Coord(1, 1));
        System.out.println(test);

        System.out.println("Popping column 9...");
        for (int i = 0; i <= GameGrid.yMax; i++) {
            test.pop(new Coord(9, i));
        }
        System.out.println(test);

        System.out.println("Squeezing...");
        test.squeezeAll();
        System.out.println(test);

//        ArrayList<Coord> testList = test.getGridAsList();
//        for (Coord t : testList) {
//            System.out.print("Color of ");
//            System.out.print(t);
//            System.out.print("is ");
//            System.out.println(test.color(t));
//        }

//        testList = test.hasLikeColoredNeighbors();
//        for (Coord t : testList) {
//            System.out.print("-Color of ");
//            System.out.print(t);
//            System.out.print("is ");
//            System.out.println(test.color(t));
//        }
        
        test = new GameGrid();
        GameGrid test2 = (GameGrid)test.clone();
        if (test.equals(test2)){
            System.out.println("Equal");
        }else{
            System.out.println("Not equal");
        }
    }

}
