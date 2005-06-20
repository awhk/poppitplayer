package simpoppit.gameboard;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.Arrays;

//import org.apache.log4j.Logger;

/**
 * <p>
 * Title: GameGrid
 * </p>
 * 
 * <p>
 * Description: The GameGrid class encapsulates the functionality of the Poppit
 * game board (not the game rules, just the board mechanics). For instance, this
 * class well initialize a board to a starting configuration (populate with
 * random balloons, etc). This class provides the means to pop a balloon, but
 * does not offer any method to decide if a balloon can legitimately be popped.
 * That functionality is the task of the {@link GameInterface}.
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

    /**
     * Creates a default-sized (15 column, 10 row) game board.
     * 
     * @see #GameGrid(int)
     * @see #GameGrid(int, int)
     * @see #GameGrid(int, int, boolean)
     */
    public GameGrid() {
        this(15);
    }

    /**
     * Creates a game board with the given number of columns and 10 rows.
     * 
     * @param aNumberOfColumns
     *            integer number of columns for this board
     * @see #GameGrid(int, int, boolean)
     */
    public GameGrid(int aNumberOfColumns) {
        this(aNumberOfColumns, 10, false);
    }

    /**
     * Creates a game board with the given number of columns and rows.
     * 
     * @param aNumberOfColumns
     *            integer number of columns for this board
     * @param aNumberOfRows
     *            integer number of rows for this board
     * @see #GameGrid(int, int, boolean)
     */
    public GameGrid(int aNumberOfColumns, int aNumberOfRows) {
        this(aNumberOfColumns, aNumberOfRows, false);
    }

    /**
     * Creates a game grid of arbitrary size and populates it with a random
     * distribution of balloons.
     * 
     * @param aNumberOfColumns
     *            integer number of columns for this board (x-dimension)
     * @param aNumberOfRows
     *            integer number of rows for this board (y-dimension)
     * @param clone
     *            boolean flag to indicate if this is a clone of an existing
     *            board (if so the population step is skipped)
     */
    private GameGrid(int aNumberOfColumns, int aNumberOfRows, boolean clone) {
        // Establish a zero-based maximum x coordinate
        GameGrid.xMax = aNumberOfColumns - 1;

        // Establish a zero-based maximum y coordinate
        GameGrid.yMax = aNumberOfRows - 1;

        // Allocate space for the "balloons"
        this.grid = new byte[aNumberOfColumns * aNumberOfRows];

        // If this not a clone operation, populate the grid with random balloons
        if (!clone) {
            for (int y = 0; y <= GameGrid.yMax; y++) {
                for (int x = 0; x <= GameGrid.xMax; x++) {
                    this.grid[(x * GameGrid.getHeight()) + y] = randColor();
                }
            }
        }
    }

    /**
     * Return a random, non-empty balloon color (byte) as defined by
     * {@link GameConsts}.
     * 
     * @return byte value representing a balloon color
     * @see GameConsts
     */
    private static byte randColor() {
        Random rand = new Random();
        int color = rand.nextInt(COLORS.length - 1) + 1;
        return COLORS[color];
    }

    /**
     * Returns the 1-based height of the grid. Used chiefly to compute the index
     * of a point given an (x,y) coordinate.
     * 
     * @return integer height of game grid
     */
    private static int getHeight() {
        // 1-based height is one greater than the already
        // computed zero-based height
        return GameGrid.yMax + 1;
    }

    /**
     * Returns a coordinate indicating the bottom right point of the game grid.
     * 
     * @return Coord containing bottom right point of this game grid
     */
    public static Coord gridSize() {
        return new Coord(GameGrid.xMax, GameGrid.yMax);
    }

    /**
     * Returns the color (byte) of the balloon at a given coordinate
     * 
     * @param aX
     *            x coordinate of desired balloon
     * @param aY
     *            y coordinate of desired balloon
     * @return byte representing color of indicated balloon
     */
    public byte getColor(int aX, int aY) {
        return this.grid[(aX * GameGrid.getHeight()) + aY];
    }

    /**
     * Returns the color (byte) of the balloon at a given coordinate.
     * 
     * @param aBalloon
     *            a {@link Coord} representing the coordinates of the desired
     *            balloon
     * @return byte representing color of indicated balloon
     */
    public byte getColor(Coord aBalloon) {
        return this.getColor(aBalloon.getX(), aBalloon.getY());
    }

    /**
     * Returns the color (string) of the balloon at a given coordinate.
     * 
     * @param aBalloon
     *            a {@link Coord} representing the coordinates of the desired
     *            balloon
     * @return string indicating color of indicated balloon
     */
    public String getColorAsString(Coord aBalloon) {
        // Get the color "byte" of the given balloon
        byte color = getColor(aBalloon);

        // If the switch falls through, this method will return "unknown" (which
        // will probably crash whatever is calling this method...)
        String result = "UNKNOWN";

        switch (color) {
        case EMPTY:
            result = "EMPTY";
            break;
        case RED:
            result = "RED";
            break;
        case YELLOW:
            result = "YELLOW";
            break;
        case PURPLE:
            result = "PURPLE";
            break;
        case GREEN:
            result = "GREEN";
            break;
        case BLUE:
            result = "BLUE";
            break;
        }
        return result;
    }

    /**
     * Given a balloon {@link Coord}, return true if that location has been
     * popped already, false otherwise.
     * 
     * @param aBalloon
     *            {@link Coord} of grid location/balloon of interest
     * @return boolean true if location is empty, false if not
     */
    public boolean isPopped(Coord aBalloon) {
        return (this.grid[(aBalloon.getX() * GameGrid.getHeight())
                + aBalloon.getY()] == EMPTY);
    }

    /**
     * Return a list of {@link Coord}s representing game grid
     * locations immediately adjacent to a given balloon.
     * 
     * @param aBalloon
     *            {@link Coord} of grid location of interest
     * @return list of up to four {@link Coord}s of grid locations
     */
    public ArrayList<Coord> neighbors(Coord aBalloon) {
        // Allocate storage for the result list
        ArrayList<Coord> result = new ArrayList<Coord>(4);

        // Loop over grid offsets up to one cell left and right...
        for (int x = -1; x <= 1; x++) {
            // ...and above and below the given coordinate
            for (int y = -1; y <= 1; y++) {

                // Skip diagonal grid locations
                if ((Math.abs(x) == Math.abs(y))
                // Skip points outside the bounds of the grid
                        || ((aBalloon.getX() + x) > GameGrid.xMax)
                        || ((aBalloon.getY() + y) > GameGrid.yMax)
                        || ((aBalloon.getX() + x) < 0)
                        || ((aBalloon.getY() + y) < 0))
                    continue;

                // If we haven't skipped this location yet, add it to the result
                // list
                result.add(new Coord(aBalloon.getX() + x, aBalloon.getY() + y));
            }
        }
        return result;
    }

    /**
     * Return a list of {@link Coord}s representing game
     * grid locations immediately adjacent to a given balloon that contain
     * balloons of the same color as the balloon at the given grid location.
     * 
     * @param aBalloon
     *            {@link Coord} of grid location of interest
     * @return list of up to four {@link Coord}s of grid locations
     */
    public ArrayList<Coord> likeColoredNeighbors(Coord aBalloon) {
        // Allocate storage for the result list
        ArrayList<Coord> result = new ArrayList<Coord>(4);

        // If the balloon we are comparing against is popped, return an empty
        // list as nothing is the same color as a popped balloon
        if (isPopped(aBalloon))
            return result;

        // Get a list of neieghboring balloons and test each of their colors,
        // adding them to the result list if they match the given balloon
        for (Coord t : neighbors(aBalloon)) {
            if (getColor(t) == getColor(aBalloon))
                result.add(t);
        }

        return result;
    }

    /**
     * Return a list of {@link Coord}s representing
     * the like-colored neighbors of a given balloon, combined with the
     * like-colored neighbors of each of those balloons, such that ultimately
     * all balloons of like color to the provided balloon that are recursively
     * adjacent are included.
     * 
     * @param aBalloon
     *            {@link Coord} of grid location of interest
     * @return list of {@link Coord}s of grid locations
     */
    public ArrayList<Coord> likeColoredNeighborChain(Coord aBalloon) {
        // Initialize a list to hold the results
        ArrayList<Coord> result = new ArrayList<Coord>();

        // Create a stack to push discovered like-colored adjacent balloons on
        // to to keep track of what we have yet to test
        Stack<Coord> toBeTested = new Stack<Coord>();

        // Push the first balloon to be tested on the stack
        toBeTested.push(aBalloon);

        // As long as we keep discovering like-colored adjacent balloons, keep
        // going
        while (!(toBeTested.isEmpty())) {

            // Loop over the list of like-colored balloons for the balloon at
            // the top of the stack
            for (Coord t : this.likeColoredNeighbors(toBeTested.pop())) {
                // If we have already found this balloon, skip it and move on to
                // the next one
                if (result.contains(t))
                    continue;

                // Otherwise, queue this balloon up for future testing
                toBeTested.push(t);
                // and add it to our result list
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Return the current grid as a list of {@link Coord}s
     * ordered by row. Basically, this method provides a {@link Coord} view of
     * the current game grid for methods that like to operate on {@link Coord}s.
     * 
     * @return list of {@link Coord}s
     */
    public ArrayList<Coord> getGridAsList() {
        // Allocate storage for result list
        ArrayList<Coord> result = new ArrayList<Coord>(grid.length);

        // Generate new {@link Coord}s and populate the result list with them.
        // This method generates coordinates along rows first to ease GUI
        // implementation. Most other methods shouldn't care about the
        // order of the returned list.
        for (int y = 0; y <= GameGrid.yMax; y++) {
            for (int x = 0; x <= GameGrid.xMax; x++) {
                result.add(new Coord(x, y));
            }
        }
        return result;
    }

    /**
     * Returns a list of {@link Coord}s of balloons in
     * the current game that have like-colored neighbors.
     * 
     * @return list of {@link Coord}s
     */
    public ArrayList<Coord> hasLikeColoredNeighbors() {
        // Initialize result list
        ArrayList<Coord> result = new ArrayList<Coord>();

        // Iterate over grid testing each balloon for like-colored neighbors.
        for (Coord t : getGridAsList()) {

            // Add any that are foudn to the result list
            if (hasLikeColoredNeighbors(t))
                result.add(t);
        }
        return result;
    }

    /**
     * Given a balloon, return true if that balloon has
     * like-colroed neighbors, false otherwise.
     * 
     * @param aBalloon
     *            {@link Coord} of grid location/balloon of interest
     * @return boolean
     */
    public boolean hasLikeColoredNeighbors(Coord aBalloon) {
        // If the size of the list returned by obtaining the list of
        // like-colored neighbors for the provided balloon is non-zero, then it
        // must have like colored neighbors
        if (likeColoredNeighbors(aBalloon).size() > 0)
            return true;
        return false;
    }

    /**
     * Given a {@link Coord}inate, pop the balloon at that location by
     * setting the color of the location to EMPTY/0.
     * 
     * @param aBalloon
     *            {@link Coord} of grid location/balloon to be popped
     */
    public void pop(Coord aBalloon) {
        this.grid[(aBalloon.getX() * GameGrid.getHeight()) + aBalloon.getY()] = EMPTY;
    }

    /**
     * Given a list of {@link Coord}s, pop all the balloons at those
     * locations.
     * 
     * @param balloonList
     *            list of {@link Coord}s of grid locations/balloons to be
     *            popped
     */
    public void popChain(ArrayList<Coord> balloonList) {
        // Iterate over Coord list
        for (Coord t : balloonList) {
            // Call pop method on each one
            this.pop(t);
        }
    }

    /**
     * Shortcut method to easily "normalize" a game grid by filling
     * in gaps created by popping balloons.
     */
    public void squeezeAll() {
        this.squeezeColumns();
        this.squeezeRows();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // Initialize a string for result text, starting the header line with
        // a tab
        String result = "\t";

        // Finish the header by numbering the columns (x-coordinate indexes)
        for (int x = 0; x <= GameGrid.xMax; x++) {
            result += x + "\t";
        }

        // Iterate over the grid, row by row
        for (int y = 0; y <= GameGrid.yMax; y++) {
            // Insert row leaders (y-coordinate indexes)
            result += "\n" + y + " :\t";
            for (int x = 0; x <= GameGrid.xMax; x++) {
                // Insert the colors of the balloons at each location as a
                // string
                result += getColorAsString(new Coord(x, y));
                // Insert a tab between columns
                result += "\t";
            }
        }
        return result;
    }

    /**
     * Return a value for the vertical bisector of the grid. This
     * value may or may not correspond with a "real" column, depending on
     * whether there are an odd or even number of columns in the grid.
     * 
     * @return float
     */
    private float centerColumn() {
        return (GameGrid.xMax / 2);
    }

    /**
     * Iterate over each column in the grid, normalizing each
     * one vertically. That is, if balloons have been popped in the column
     * leaving spaces, "float" lower balloons up to fill in the gaps.
     * 
     */
    private void squeezeColumns() {
        // For each column in the grid
        for (int x = 0; x <= GameGrid.xMax; x++) {
            // squeeze the column
            squeezeColumn(x);
        }
    }

    /**
     * Given a column, normalize it to fill in spaces left by
     * popping balloons.
     * 
     * @param aColumn
     *            the x-coordinate of the column to be normalized
     */
    private void squeezeColumn(int aColumn) {
        // Boolean tag to denote the fact that we have seen a popped balloon in
        // processing this column
        boolean sawPopped = false;

        // Iterate over the length of the given column
        for (int y = 0; y <= GameGrid.yMax; y++) {
            // If we see a popped balloon, note it and keep processing the
            // column. This will fall through the loop if *every* balloon in the
            // column is popped.
            if (this.isPopped(new Coord(aColumn, y))) {
                sawPopped = true;
                continue;
            }

            // If we get here, we have encountered an unpopped balloon in the
            // column.

            // If we have previously seen a popped balloon, then the fact that
            // we have arrived here means that an unpopped balloon exists in the
            // column below a popped one, so we need to percolate the unpopped
            // balloon upwards by swapping this space with the (empty) one above
            // it.
            if (sawPopped) {
                // Temporarily store the value of the location above this one
                byte temp = grid[(aColumn * GameGrid.getHeight()) + (y - 1)];
                // Copy the value of the current location to the location above
                // this
                grid[(aColumn * GameGrid.getHeight()) + (y - 1)] = grid[(aColumn * GameGrid
                        .getHeight())
                        + y];
                // Copy the stored value to the current location
                grid[(aColumn * GameGrid.getHeight()) + y] = temp;
                // More squeezing might be in order for this column, so squeeze
                // again until no unpopped balloons are found below popped ones,
                // in which case this block won't be entered and the recursive
                // calls will stop.
                squeezeColumn(aColumn);
            }
        }
    }

    /**
     * Given a column, returns true if the column is empty, false
     * otherwise.
     * 
     * @param aColumn
     *            the x-coordinate of the column to be tested
     * @return boolean
     */
    private boolean columnIsEmpty(int aColumn) {
        // If the given column is not in the grid, return true (because balloon
        // filled columns are isolated to the grid space)
        if (aColumn < 0 || aColumn > GameGrid.xMax)
            return true;
        // Iterate over the column
        for (int y = 0; y <= GameGrid.yMax; y++) {
            // If any locations contain unpopped balloons, return false
            if (this.grid[(aColumn * GameGrid.getHeight()) + y] != EMPTY)
                return false;
        }
        return true;
    }

    /**
     * Loop over the rows of the grid moving empty rows to the
     * outside and clustering non-empty rows together, ideally in the middle of
     * the grid.
     * 
     */
    private void squeezeRows() {
        // Run squeeze row method once, noting if it did anything.
        boolean swapped = squeezeRow();
        // If it did, run it again and again until it does nothing.
        while (swapped) {
            swapped = squeezeRow();
        }
    }

    /**
     * Iterate once over the rows of the grid checking for empty
     * columns. If an empty column is found on the "center side" of a full
     * column, swap it with the nearest outside column to migrate it out.
     * 
     * @return boolean true if a column was swapped, false if nothing was done
     */
    private boolean squeezeRow() {
        // Initialize the return value to false
        boolean swapped = false;

        // Examine the left half of the grid
        for (int x = 0; x <= centerColumn(); x++) {
            // If we have encountered an empty column to the right of a
            // non-empty column, swap them
            if (columnIsEmpty(x) && !(columnIsEmpty(x - 1))) {
                // Note that a swap was performed
                swapped = true;
                // Swap this column with the one to its left
                swapColumns(x, x - 1);
            }
        }

        // Examine the right half of the grid
        for (int x = GameGrid.xMax; x >= centerColumn(); x--) {
            // If we have encountered an empty column to the left of a
            // non-empty column, swap them
            if (columnIsEmpty(x) && !(columnIsEmpty(x + 1))) {
                // Note that a swap was performed
                swapped = true;
                // Swap this column with the one to its right
                swapColumns(x, x + 1);
            }
        }
        return swapped;
    }

    /**
     * Given two x-coordinates of columsn in the grid, swap the
     * columns
     * 
     * @param column1
     *            x-coordinate of one of the columsn to swap
     * @param column2
     *            x-coordinate of the column to swap it with
     */
    private void swapColumns(int column1, int column2) {
        // Iterate of the height of a column
        for (int y = 0; y <= GameGrid.yMax; y++) {
            // Temporarily store the value of the current location in column 1
            byte temp = grid[(column1 * GameGrid.getHeight()) + y];
            // Set the value of the current location in column 1 to the value of
            // the current location in column 2
            grid[(column1 * GameGrid.getHeight()) + y] = grid[(column2 * GameGrid
                    .getHeight())
                    + y];
            // Set the value of current location of column 2 to the stored value
            grid[(column2 * GameGrid.getHeight()) + y] = temp;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object aGrid) {
        // If the given object is not a grid, then it can't be equal to this
        // grid
        if (!(aGrid instanceof GameGrid))
            return false;
        // Use the equals method of the Arrays class to compare the grid spaces
        // of the two objects
        return Arrays.equals(this.grid, ((GameGrid) aGrid).grid);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        // Create a new GameGrid object with the "clone" flag on
        GameGrid result = new GameGrid(GameGrid.xMax + 1, GameGrid.yMax + 1,
                true);
        // Use the optimized system array copy to duplicate this grid's data
        // array
        System.arraycopy(this.grid, 0, result.grid, 0, this.grid.length);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable
     */
    public int compareTo(Object aGrid) {
        // Initialize result vallue to zero
        int result = 0;
        // Begin compare block, iterating over the grid space
        compare: for (int i = 0; i < grid.length; i++) {
            // Set result to the difference of each corresponding point in
            // this grid and the given grid
            result = grid[i] - ((GameGrid) aGrid).grid[i];
            // If the difference is ever non-zero then the grids are not
            // equal, and we can order by the first difference, so break out
            // of the comparison block
            if (result != 0)
                break compare;
        }
        // And return the difference (which will be zero if the grids are equal)
        return result;
    }

    private static int xMax;

    private static int yMax;

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

        Coord coord = new Coord(13, 8);
        System.out.println("Neighbors of " + coord + " are:");
        for (Coord t : test.neighbors(coord)) {
            System.out.println(t);
        }

        System.out.println("Like-colored neighboring balloons to " + coord
                + " are:");
        for (Coord t : test.likeColoredNeighbors(coord)) {
            System.out.println(t);
        }

        System.out.println("Like-colored neighbor chain for " + coord + " is:");
        for (Coord t : test.likeColoredNeighborChain(coord)) {
            System.out.println(t);
        }
        System.out.println("Popping the like-colored neighbor chain for "
                + coord + " ...");
        test.popChain(test.likeColoredNeighborChain(coord));
        test.pop(coord);
        System.out.println(test);

        System.out.println("Popping column 9...");
        for (int i = 0; i <= GameGrid.yMax; i++) {
            test.pop(new Coord(9, i));
        }
        System.out.println(test);

        System.out.println("Squeezing...");
        test.squeezeAll();
        System.out.println(test);

        // ArrayList<Coord> testList = test.getGridAsList();
        // for (Coord t : testList) {
        // System.out.print("Color of ");
        // System.out.print(t);
        // System.out.print("is ");
        // System.out.println(test.color(t));
        // }

        // testList = test.hasLikeColoredNeighbors();
        // for (Coord t : testList) {
        // System.out.print("-Color of ");
        // System.out.print(t);
        // System.out.print("is ");
        // System.out.println(test.color(t));
        // }

        test = new GameGrid();
        GameGrid test2 = (GameGrid) test.clone();
        if (test.equals(test2)) {
            System.out.println("Equal");
        } else {
            System.out.println("Not equal");
        }
        if (test.compareTo(test2) ==  0) {
            System.out.println("Equal");
        } else {
            System.out.println("Not equal");
        }
    }

}
