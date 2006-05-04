package simpoppit.gameboard;

import java.util.*;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import simpoppit.gui.*;

/**
 * <p>
 * Title: GameInterface
 * </p>
 * 
 * <p>
 * Description: The game interface class provides the means to initialize a
 * poppit game and play it through, exposing available moves, modifying the
 * board in response to moves made, and making sure that the game rules are
 * followed and the board state remains consistent. This is the class that
 * should be used by any player of Poppit to invoke and interact with a Poppit
 * game instance. This class makes heavy use of the {@link GameGrid}.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Andrew W. Henry
 * @version 1.0
 * @see GameGrid
 */
public class GameInterface implements Cloneable, Comparable, Serializable {

    /**
     * Default constructor: creates a default-sized game grid and initializes
     * it.
     * 
     * @see #GameInterface(int, int)
     */
    public GameInterface() {
        this(15, 10);
    }

    /**
     * This constructor creates an arbitrary-sized Poppit game instance
     * 
     * @param aX
     *            integer number of rows for the game grid
     * @param aY
     *            integer number of columns for the game grid
     */
    public GameInterface(int aX, int aY) {
        // Inititalize the game grid to the given size
        this.gameBoard = new GameGrid(aX, aY);
        // Set the default event action
        this.action = "none";
        // Init the coordinate list, used to identify event recipients
        this.coordList = new ArrayList<Coord>();
        // Init the move list, used to replay games
        this.moveList = new LinkedList<Coord>();
        // Init the event listener list
        GameInterface.gameListeners = new LinkedList<GameListener>();
        // Init the static maximum score value for this game
        GameInterface.maxScore = aX * aY;
        // Init the starting accumulated score for this game session
        this.score = 0;
        // Store the starting layout for replay purposes
        this.startBoard = (GameGrid)this.gameBoard.clone();
        //System.out.println("New game created.");
    }

    /**
     * This contructor is used by the clone method to propogate the
     * accumulated score as well as avoid clearing the list of game event
     * listeners. The score could be computed, but since it is readily at hand
     * and we have a dedicated constructor anyway, might as well just pass it
     * and save the processor time.
     * 
     * @param newGrid
     *            an existing game grid to use for this new game instance
     * @param aScore
     */
    private GameInterface(GameGrid newGrid, GameGrid startGrid, int aScore, Queue<Coord>moves) {
        this.gameBoard = newGrid;
        this.startBoard = startGrid;
        this.coordList = new ArrayList<Coord>();
        this.moveList = new LinkedList<Coord>();
        for (Coord item : moves){
            this.moveList.offer(item);
        }
        this.action = "none";
        this.score = aScore;
    }

    /**
     * Returns a list of {@link Coord}s of locations in the
     * current game grid that are eligible to be popped according to the game
     * rules.
     * 
     * @return list of {@link Coord}s
     */
    public ArrayList<Coord> getPossibleMoves() {
        return gameBoard.hasLikeColoredNeighbors();
    }

    /**
     * Given a balloon/grid location, return true if the location
     * contains a balloon that is eligible for popping according to the game
     * rules.
     * 
     * @param aBalloon
     *            {@link Coord}inate of balloon/grid location of interest
     * @return boolean
     */
    public boolean isPoppable(Coord aBalloon) {
        if (this.gameBoard.hasLikeColoredNeighbors(aBalloon))
            return true;
        return false;
    }

    /**
     * @see GameGrid#isPopped(Coord)
     */
    public boolean isPopped(Coord aBalloon) {
        return this.gameBoard.isPopped(aBalloon);
    }

    /**
     * @see GameGrid#getColorAsString(Coord)
     */
    public String getColorAsString(Coord aBalloon) {
        return gameBoard.getColorAsString(aBalloon);
    }

    /**
     * @see GameGrid#getColor(Coord)
     */
    public byte getColor(Coord aBalloon) {
        return gameBoard.getColor(aBalloon);
    }

    /**
     * @see GameGrid#gridSize()
     */
    public Coord getGridSize() {
        return GameGrid.gridSize();
    }

    /**
     * @see GameGrid#getGridAsList()
     */
    public ArrayList<Coord> getGridAsList() {
        return gameBoard.getGridAsList();
    }

    /**
     * Returns the {@link GameGrid} used by this game instance
     * 
     * @return current {@link GameGrid}
     */
    public GameGrid getGrid() {
        return this.gameBoard;
    }

    /**
     * Returns the value of the current score for this game instance.
     * 
     * @return current integer score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Returns the maximum possible score for this game instance.
     * This score may not be attainable, and represents only the score that
     * could be achieved if the board was completely solvable (i.e. all the
     * balloon could ultimately be popped).
     * 
     * @return integer maximum score
     */
    public int getMaxScore() {
        return GameInterface.maxScore;
    }

    /**
     * Returns the game state to initial settings. Does not revert to
     * original game grid layout, but generates a new one of the same size.
     * Sends an "update" event to all registered listeners.
     * 
     */
    public void resetGame() {
        // Generate new game grid of the same size as the current one
        this.gameBoard = new GameGrid(GameGrid.gridSize().getX() + 1, GameGrid
                .gridSize().getY() + 1);
        // Prepare a list of affected locations to send update messages to
        this.coordList.addAll(this.gameBoard.getGridAsList());
        // Reset score
        this.score = 0;
        // Select the "update" action
        this.action = "update";
        // Send the event
        this.fireGameEvent();
    }
    
    
    /**
     * Returns the game state to initial settings. Reverts to
     * original game grid layout.
     * Sends an "update" event to all registered listeners.
     * 
     */
    public void restartGame() {
        this.restartGame(true);
    }
    
    /**
     * Returns the game state to initial settings. Reverts to
     * original game grid layout.
     * Sends an "update" event to all registered listeners.
     * 
     */
    public void restartGame(boolean resetmoves) {
        // Copy the starting board into the current one
        this.gameBoard = (GameGrid)this.startBoard.clone();
        // Prepare a list of affected locations to send update messages to
        this.coordList.addAll(this.gameBoard.getGridAsList());
        // Reset score
        this.score = 0;
        // Select the "update" action
        this.action = "update";
        // Clear move list maybe
        if (resetmoves){
            this.moveList.clear();
        }
        // Send the event
        this.fireGameEvent();
    }
    
    /**
     * Returns the game state to initial settings. Reverts to
     * original game grid layout.
     * Sends an "update" event to all registered listeners.
     * 
     */
    public void fastRestartGame() {
        // Copy the starting board into the current one
        this.gameBoard = (GameGrid)this.startBoard.clone();
        // Reset score
        this.score = 0;
        // Clear move list maybe
        this.moveList.clear();
    }
    
    /**
     * Saves the current game state to a file.
     * 
     */
    public void saveGame(String filename) {
        try{
            FileOutputStream f_out = new FileOutputStream(filename);
            ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
            obj_out.writeObject ( this );
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Load the current game state from a file.
     * 
     */
    public void loadGame(String filename) {
        try{
            FileInputStream f_in = new FileInputStream(filename);
            ObjectInputStream obj_in = new ObjectInputStream (f_in);
            GameInterface loaded = (GameInterface)obj_in.readObject();
            this.startBoard = loaded.startBoard;
            this.gameBoard = loaded.getGrid();
            this.score = loaded.getScore();
            this.moveList = loaded.moveList;
            this.coordList.addAll(this.gameBoard.getGridAsList());
            this.action = "update";
            this.fireGameEvent();
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }  
    }
    
    /**
     * Replays the current game from the move list.
     * 
     */
    public void replayGame() {
        // Copy the starting board into the current one
        this.gameBoard = (GameGrid)this.startBoard.clone();
        Queue<Coord> localMoves = this.moveList;
        this.moveList = new LinkedList<Coord>();
        // Prepare a list of affected locations to send update messages to
        this.coordList.addAll(this.gameBoard.getGridAsList());
        // Reset score
        this.score = 0;
        // Select the "update" action
        this.action = "update";
        // Send the event
        this.fireGameEvent();
//        for (Coord item : this.moveList){
//            System.out.println("Highlighting item " + item);
//            this.highlight(item);
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                System.out.println("Failed to sleep - " + e);
//            }
//            System.out.println("Popping item " + item);
//            this.pop(item);
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                System.out.println("Failed to sleep - " + e);
//            }
//        }
        while(localMoves.peek() != null){
            this.highlight(localMoves.peek());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Failed to sleep - " + e);
            }
            System.out.println("Popping item " + localMoves.peek());
            this.pop(localMoves.poll());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Failed to sleep - " + e);
            }
        }
    }
    
    /**
     * Replays the current game from the move list.
     * 
     */
    public void replayGameGUI() {
        SimPoppitGui gamegui = new SimPoppitGui(this, false);
        gamegui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.restartGame(false);
        gamegui.setVisible(true);
        JOptionPane.showMessageDialog(gamegui, "Click OK to watch playback",
                "Game Solved", JOptionPane.DEFAULT_OPTION);
        Queue<Coord> localMoves = this.moveList;
        this.moveList = new LinkedList<Coord>();
        // Prepare a list of affected locations to send update messages to
        this.coordList.addAll(this.gameBoard.getGridAsList());
        // Reset score
        this.score = 0;
        // Select the "update" action
        this.action = "update";
        // Send the event
        this.fireGameEvent();
//        for (Coord item : this.moveList){
//            System.out.println("Highlighting item " + item);
//            this.highlight(item);
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                System.out.println("Failed to sleep - " + e);
//            }
//            System.out.println("Popping item " + item);
//            this.pop(item);
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                System.out.println("Failed to sleep - " + e);
//            }
//        }
        while(localMoves.peek() != null){
            this.highlight(localMoves.peek());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Failed to sleep - " + e);
            }
            System.out.println("Popping item " + localMoves.peek());
            this.pop(localMoves.poll());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Failed to sleep - " + e);
            }
        }
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println("Failed to sleep - " + e);
        }
    }

    /**
     * Returns the score value of the move represented by the given
     * location. That is, if the given location was popped (assuming it is
     * eligible), how much that move would increase the score of this game.
     * 
     * @param aBalloon
     *            {@link Coord}inate of balloon/grid location of interest
     * @return integer score value of move
     */
    public int valueOfMove(Coord aBalloon) {
        // If the given location doe snot represent a valid move, its score
        // value is zero
        if (!this.isPoppable(aBalloon))
            return 0;
        // Otherwise, return the number of balloons that would be popped by this
        // move
        return this.gameBoard.likeColoredNeighborChain(aBalloon).size();
    }

    /**
     * Returns true if there are no more moves left in this game,
     * false otherwise.
     * 
     * @return boolean
     */
    public boolean isGameOver() {
        for (Coord t : getGridAsList()){
            if (this.gameBoard.hasLikeColoredNeighbors(t)) return false;
        }
        return true;
    }

    /**
     * Given a balloon/grid location, pop the balloon at that location if
     * eligible, and take care of other tasks necessary to maintain game
     * integrity after a move takes place (squeezing columns and rows, notifying
     * registered listeners, etc). Return true if the pop was successful, false
     * if not.
     * 
     * @param aBalloon
     *            {@link Coord}inate of balloon/grid location desired to pop
     * @return boolean
     */
    public boolean pop(Coord aBalloon) {
        // If the given location does not contain a poppable balloon, return
        // failure
        if (!this.isPoppable(aBalloon))
            return false;
        // If we have a poppable balloon, add it to the move history 
        this.moveList.offer(aBalloon);
        // Start by un-highlighting the current balloon group. This is done for
        // GUI purposes, so the highlighted locations do not linger after the
        // balloons have been popped.
        this.unHighlight(aBalloon);
        // Clear the "affected balloon" store
        this.coordList.clear();
        // Collect the group of balloons that will be popped
        this.coordList
                .addAll(this.gameBoard.likeColoredNeighborChain(aBalloon));
        // Adjust the score by the number of balloons popped
        this.score += this.coordList.size();
        // Set the current game action to "pop"
        this.action = "pop";
        // Pop the balloons
        this.gameBoard.popChain(this.coordList);
        // Notify registered listeners about what happened
        this.fireGameEvent();
        // Normalize the board layout
        this.gameBoard.squeezeAll();
        // Clear the "affected balloon" store again
        this.coordList.clear();
        // Load the "affected" store with the full grid
        // Note: probable optimization opportunity
        this.coordList.addAll(this.gameBoard.getGridAsList());
        // Set the current game action to "update"
        this.action = "update";
        // Notify registered listeners
        this.fireGameEvent();
        // Test if this was the final move for this game
        if (this.isGameOver()) {
            // If so, load the "gameover" event
            this.action = "gameover";
            // And fire
            this.fireGameEvent();
        }
        return true;
    }

    /**
     * Pop all eligible balloons in the current game. This is not a
     * legal action, but is a cheat added for troubleshooting purposes to
     * quickly pop multiple balloons and finish a game rapidly.
     * 
     */
    public void popAll() {
        for (Coord t : getPossibleMoves()) {
            unHighlight(t);
            pop(t);
        }
    }

    /**
     * Given a balloon/grid location, if it contains a balloon that
     * is eligible to be popped, send the "highlight" game event to that balloon
     * and all other balloons in the same balloon group (like-colored and
     * adjacent).
     * 
     * @param aBalloon
     *            {@link Coord}inate of the balloon to potentially highlight
     */
    public void highlight(Coord aBalloon) {
        // If the given balloon is not eligible for popping, don't highlight it
        if (!this.isPoppable(aBalloon))
            return;
        // Add all the balloons in this group to the "affected balloon" store
        this.coordList
                .addAll(this.gameBoard.likeColoredNeighborChain(aBalloon));
        // Set the game event to "highlight"
        this.action = "highlight";
        // And fire off a game event to registered listeners
        this.fireGameEvent();
    }

    /**
     * Given a balloon/grid location, send the "unhighlight" game
     * event to that balloon and all other balloons in the same balloon group
     * (like-colored and adjacent).
     * 
     * @param aBalloon
     *            {@link Coord}inate of the balloon to unhighlight
     */
    public void unHighlight(Coord aBalloon) {
        // Add all the balloons in this group to the "affected balloon" store
        this.coordList
                .addAll(this.gameBoard.likeColoredNeighborChain(aBalloon));
        // Set the game event to "unhighlight"
        this.action = "unhighlight";
        // And fire off a game event to registered listeners
        this.fireGameEvent();
    }

    /**
     * Register a class to allow it to receive event
     * notifications when the game state changes.
     * 
     * @param listener
     *            a class that implements the {@link GameListener} interface
     * @see GameEvent
     */
    public synchronized void addGameListener(GameListener listener) {
        GameInterface.gameListeners.add(listener);
    }

    /**
     * Unregister a class to stop it from receiving event
     * notifications when the game state changes.
     * 
     * @param listener
     *            a class that implements the {@link GameListener} interface
     * @see GameEvent
     */
    public synchronized void removeGameListener(GameListener listener) {
        GameInterface.gameListeners.remove(listener);
    }

    /**
     * Distribute the current game event to registered listeners
     * 
     * @see GameEvent
     */
    private synchronized void fireGameEvent() {
        // If there are no listeners, don't waste any more time
        if (GameInterface.gameListeners.isEmpty())
            return;
        // Create a new game event
        GameEvent event = new GameEvent(this, this.coordList, this.action);
        // Send each registered listener the event
        for (GameListener t : GameInterface.gameListeners) {
            t.gameEventReceived(event);
        }
        // Clear the affected balloon store and reset the current event type
        this.coordList.clear();
        this.action = "none";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object aGame) {
        // If the passed Object is a GameInterface, and the game grids of both
        // GameInterfaces are equivalent, then the GameInterfaces are
        // equivalent, because if the boards are the same then the score must
        // also be the same as path is not a consideration. Other fields of this
        // class are either transient or static, so they are of no concern when
        // judging equality.
        if ((aGame instanceof GameInterface)
                && (((GameInterface) aGame).gameBoard.equals(this.gameBoard)))
            return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        // To clone the underlying game grid is to clone the GameInterface, for
        // the most part. The score is passed along, although it could just as
        // easily be recomputed by the cloned instance. Other fields of this
        // class are either transient or static, so they are of no concern when
        // cloning.
        GameInterface result = new GameInterface((GameGrid) this.gameBoard
                .clone(), (GameGrid) this.startBoard
                .clone(), this.score, this.moveList);
        return result;
    }

    /**
     * @see java.lang.Comparable
     */
    public int compareTo(Object aGame) {
        // Comparing GameInterfaces is essentially comparing the underlying
        // grids, so that is all we do.
        return this.gameBoard.compareTo(((GameInterface) aGame).gameBoard);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // Initialize a result string
        String result = "";
        // Insert score information
        result += "Score is (current/max): " + this.score + "/" + GameInterface.maxScore + "\n";
        // Add the game grid's string representation to the output
        result += "Gameboard is:\n" + this.gameBoard + "\n";
        result += "Move list is:\n";
        for (Coord item : this.moveList){
            result += item + ", ";
        }
        return result;
    }

    private int score;

    private static int maxScore;

    private GameGrid gameBoard;

    private final ArrayList<Coord> coordList;

    private String action;

    private static LinkedList<GameListener> gameListeners;
    
    private Queue<Coord> moveList;
    
    private GameGrid startBoard;
    
    static final long serialVersionUID = 123456;

    /**
     * @param args
     */
    public static void main(String[] args) {
        GameInterface test = new GameInterface(5, 5);
        GameInterface testClone = (GameInterface) test.clone();
        System.out.println("Test is " + test);
        test.pop(test.getPossibleMoves().get(0));
        System.out.println("Test is " + test);
        System.out.println("TestClone is " + testClone);
        if (test.equals(testClone)) {
            System.out.println("Equal");
        } else {
            System.out.println("Not equal");
        }
    }

}
