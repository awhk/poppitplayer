import java.util.*;

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

public class GameGrid implements Cloneable, Comparable {

    public GameGrid() {
        this(15);
    }

    public GameGrid(int aNumberOfColumns) {
        this(aNumberOfColumns, 10);
    }

    public GameGrid(int aNumberOfColumns, int aNumberOfRows) {
        if ((aNumberOfColumns % 2) == 0) {
            aNumberOfColumns++;
            System.out.println("Number of columns must be odd.  Incremented.");
        }
        this.grid = new BalloonColumn[aNumberOfColumns];
        for (int i = 0; i < this.getSize(); i++) {
            this.grid[i] = new BalloonColumn(aNumberOfRows);
        }
        this.xMax = aNumberOfColumns - 1;
        this.yMax = aNumberOfRows - 1;
        this.bottomRight = new Coord(aNumberOfColumns - 1, aNumberOfRows - 1);
    }

    public int getSize() {
        return grid.length;
    }

    public Coord gridSize() {
        return this.bottomRight;
    }

    public Balloon.Color color(int aX, int aY) {
        return this.color(new Coord(aX, aY));
    }

    public Balloon.Color color(Coord aBalloon) {
        if (aBalloon.getX() > this.xMax || aBalloon.getY() > this.yMax) {
            return null;
        }
        return this.grid[aBalloon.getX()].color(aBalloon.getY());
    }

    public Balloon getBalloon(int aX, int aY) {
        return this.getBalloon(new Coord(aX, aY));
    }

    public Balloon getBalloon(Coord aBalloon) {
        return this.grid[aBalloon.getX()].getBalloon(aBalloon.getY());
    }

    public BalloonColumn[] getColumns() {
        return this.grid;
    }

    public BalloonColumn getColumn(int aColumn) {
        return this.grid[aColumn];
    }

    public ArrayList<Coord> neighbors(Coord aBalloon) {
        ArrayList<Coord> result = new ArrayList<Coord>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coord myTestPoint = new Coord(aBalloon.getX() + i, aBalloon
                        .getY()
                        + j);
                if (bottomRight.isBeyond(myTestPoint) && !(i == j)
                        && !(aBalloon.isDiagonalTo(myTestPoint))) {
                    result.add(new Coord(aBalloon.getX() + i, aBalloon.getY()
                            + j));
                }
            }
        }
        return result;
    }

    public ArrayList<Coord> likeColoredNeighbors(Coord aBalloon) {
        ArrayList<Coord> result = new ArrayList<Coord>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coord myTestPoint = new Coord(aBalloon.getX() + i, aBalloon
                        .getY()
                        + j);
                if (myTestPoint.getX() >= 0
                        && myTestPoint.getY() >= 0
                        && bottomRight.isBeyond(myTestPoint)
                        && !(i == j)
                        && !(aBalloon.isDiagonalTo(myTestPoint))
                        && !(this.getBalloon(myTestPoint)).isPopped()
                        && !(this.getBalloon(aBalloon)).isPopped()
                        && (this.getBalloon(myTestPoint).equals(this
                                .getBalloon(aBalloon)))) {
                    result.add(new Coord(aBalloon.getX() + i, aBalloon.getY()
                            + j));
                }
            }
        }
        return result;
    }

    public ArrayList<Coord> likeColoredNeighborChain(Coord aBalloon) {
        ArrayList<Coord> result = new ArrayList<Coord>();
        ArrayList<Coord> alreadyVisited = new ArrayList<Coord>();
        Stack<Coord> toBeTested = new Stack<Coord>();
        ArrayList<Coord> temp = new ArrayList<Coord>();
        alreadyVisited.add(aBalloon);
        toBeTested.push(aBalloon);
        while (!(toBeTested.isEmpty())) {
            temp = this.likeColoredNeighbors(toBeTested.pop());
            for (Coord t : temp) {
                if (alreadyVisited.contains(t))
                    continue;
                toBeTested.push(t);
                alreadyVisited.add(t);
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Coord> getGridAsList() {
        ArrayList<Coord> result = new ArrayList<Coord>(this.bottomRight.getX()
                * this.bottomRight.getY());
        for (int i = 0; i <= this.bottomRight.getX(); i++) {
            for (int j = 0; j <= this.bottomRight.getY(); j++) {
                result.add(new Coord(i, j));
            }
        }
        return result;
    }

    public ArrayList<Coord> getGridAsListByRow() {
        ArrayList<Coord> result = new ArrayList<Coord>(this.bottomRight.getX()
                * this.bottomRight.getY());
        for (int i = 0; i <= this.bottomRight.getY(); i++) {
            for (int j = 0; j <= this.bottomRight.getX(); j++) {
                result.add(new Coord(j, i));
            }
        }
        return result;
    }
	
	public Balloon[] getGridAsBalloonArray(){
		ArrayList<Balloon> myList = new ArrayList<Balloon>();
		for(Coord t : this.getGridAsList()){
			myList.add(this.getBalloon(t));
		}
		return myList.toArray(new Balloon[myList.size()]);
	}
    
    public BalloonBits getGridAsBalloonBits(){
        return new BalloonBits(this.getGridAsBalloonArray());
    }
	
	public BalloonInt getGridAsBalloonInt(){
		return new BalloonInt(this.getGridAsBalloonArray());
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
        this.grid[aBalloon.getX()].pop(aBalloon.getY());
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
        for (int i = 0; i <= this.yMax; i++) {
            for (int j = 0; j <= this.xMax; j++) {
                result += this.grid[j].color(i);
                result += "\t";
            }
            result += "\n";
        }
        return result;
    }

    private int centerColumn() {
        int length = this.getSize();
        if (length == 1)
            return 0;
        return ((length - 1) / 2);
    }

    private void setColumns(BalloonColumn[] aNewGrid) {
        for (int i = 0; i < this.getSize(); i++) {
            this.grid[i] = (BalloonColumn) aNewGrid[i].clone();
        }
    }

    private void squeezeColumns() {
        for (BalloonColumn t : this.grid) {
            t.squeeze();
        }
    }

    private void squeezeRows() {
        ArrayList<BalloonColumn> myGrid = new ArrayList<BalloonColumn>();
        boolean swapped = false;
        for (int i = 0; i < this.getSize(); i++) {
            myGrid.add(i, this.grid[i]);
        }
        int leftOfCenter = 0;
        int rightOfCenter = 0;
        for (int i = 0; i < this.getSize(); i++) {
            if (myGrid.get(i).isEmpty())
                continue;
            if (i < this.centerColumn()) {
                leftOfCenter++;
            } else {
                rightOfCenter++;
            }
        }
        if (leftOfCenter == 0 & rightOfCenter == 0) {
            return;
        }
        if (myGrid.get(this.centerColumn()).isEmpty()) {
            if (leftOfCenter > rightOfCenter) {
                Collections.swap(myGrid, this.centerColumn() - 1, this
                        .centerColumn());
            } else {
                Collections.swap(myGrid, this.centerColumn() + 1, this
                        .centerColumn());
            }
        }
        for (int i = 0; i < this.getSize(); i++) {
            if (myGrid.get(i).isEmpty()) {
                if (i == 0 || i == this.xMax || i == this.centerColumn())
                    continue;
                if (i < this.centerColumn()) {
                    if (myGrid.get(i).isEmpty() & myGrid.get(i - 1).isEmpty())
                        continue;
                    Collections.swap(myGrid, i - 1, i);
                    swapped = true;
                } else {
                    if (myGrid.get(i).isEmpty() & myGrid.get(i + 1).isEmpty())
                        continue;
                    Collections.swap(myGrid, i + 1, i);
                    swapped = true;
                }
            }
        }
        for (int i = 0; i < this.getSize(); i++) {
            this.grid[i] = myGrid.get(i);
        }
        if (swapped)
            squeezeRows();
    }

    public boolean equals(Object aGrid) {
        if (!(aGrid instanceof GameGrid))
            return false;
        if (!(((GameGrid) aGrid).getSize() == this.getSize()))
            return false;
		Balloon[] thisGrid = this.getGridAsBalloonArray();
		Balloon[] otherGrid = ((GameGrid)aGrid).getGridAsBalloonArray();
		for(int i=0; i<this.getSize(); i++){
			if (thisGrid[i] != otherGrid[i]) return false;
		}
        return true;
    }

    public Object clone() {
        GameGrid result = new GameGrid(this.gridSize().getX() + 1, this
                .gridSize().getY() + 1);
        result.setColumns(this.grid);
        return result;
    }
    
    public int compareTo(Object aGrid){
		int result = 0;
		Balloon[] thisGrid = this.getGridAsBalloonArray();
		Balloon[] otherGrid = ((GameGrid)aGrid).getGridAsBalloonArray();
		for (int i=0; i<thisGrid.length; i++){
			result = thisGrid[i].getNumber() - otherGrid[i].getNumber();
			if (result != 0) break;
		}
		return result;
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
        for (int i = 0; i <= test.yMax; i++) {
            test.pop(new Coord(9, i));
        }
        System.out.println(test);

        System.out.println("Squeezing...");
        test.squeezeAll();
        System.out.println(test);

        ArrayList<Coord> testList = test.getGridAsList();
        for (Coord t : testList) {
            System.out.print("Color of ");
            System.out.print(t);
            System.out.print("is ");
            System.out.println(test.color(t));
        }

        testList = test.hasLikeColoredNeighbors();
        for (Coord t : testList) {
            System.out.print("-Color of ");
            System.out.print(t);
            System.out.print("is ");
            System.out.println(test.color(t));
        }
        
        test = new GameGrid();
        GameGrid test2 = (GameGrid)test.clone();
        if (test.equals(test2)){
            System.out.println("Equal");
        }else{
            System.out.println("Not equal");
        }
    }

}
