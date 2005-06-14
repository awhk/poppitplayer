package simpoppit.gameboard;
import java.util.*;

import simpoppit.gui.*;

/**
 * <p>Title: GameInterface</p>
 *
 * <p>Description: Game interface class</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Andrew W. Henry
 * @version 1.0
 */
public class GameInterface implements Cloneable, Comparable{
	
	public GameInterface(){
		this(15, 10);
	}
	
	public GameInterface(int aX, int aY){
		this.gameBoard = new GameGrid(aX, aY, false);
        this.action = "none";
        this.coordList = new ArrayList<Coord>(20);
        GameInterface.gameListeners = new LinkedList<GameListener>();
        GameInterface.maxScore = aX * aY;
        this.score = 0;
	}
    
    public GameInterface(GameGrid newGrid, int aScore){
        this.gameBoard = newGrid;
        this.coordList = new ArrayList<Coord>(20);
//        GameInterface.gameListeners = newGameListeners;
//        GameInterface.maxScore = (this.gameBoard.gridSize().getX()+1)*(this.gameBoard.gridSize().getY()+1);
        this.action = "none";
        this.score = aScore;
    }
    
    public ArrayList<Coord> getCoordList(){
        return this.coordList;
    }
	
	public ArrayList<Coord> possibleMoves(){
		return gameBoard.hasLikeColoredNeighbors();
	}
	
	public boolean isPoppable(Coord aBalloon){
        if (this.gameBoard.hasLikeColoredNeighbors(aBalloon)) return true;
		return false;
	}
    
    public boolean isPopped(Coord aBalloon){
        return this.gameBoard.isPopped(aBalloon);
    }
    
    public String getColorAsString(Coord aBalloon){
        return gameBoard.getColorAsString(aBalloon);
    }
    
    public byte getColor(Coord aBalloon){
        return gameBoard.getColor(aBalloon);
    }
    
    public Coord getGridSize(){
        return gameBoard.gridSize();
    }
    
    public ArrayList<Coord> getGridAsList() {
        return gameBoard.getGridAsList();
    }
    
    public GameGrid getGrid(){
        return this.gameBoard;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public int getMaxScore(){
        return GameInterface.maxScore;
    }
    
    public String getAction(){
        return this.action;
    }
    
    public void resetGame(){
        this.gameBoard = new GameGrid(this.gameBoard.gridSize().getX()+1, this.gameBoard.gridSize().getY()+1, false);
        this.coordList.addAll(this.gameBoard.getGridAsList());
        //GameInterface.maxScore = this.gameBoard.gridSize().getX() * this.gameBoard.gridSize().getY();
        this.score = 0;
        this.action = "update";
        this.fireGameEvent();
    }
    
    public int valueOfMove(Coord aBalloon){
        if (!this.isPoppable(aBalloon)) return 0;
        return this.gameBoard.likeColoredNeighborChain(aBalloon).size() + 1;
    }
    
    public boolean gameOver(){
        if (this.possibleMoves().isEmpty()) return true;
        return false;
    }
    
    public boolean pop(Coord aBalloon){
        //System.out.println("Popping " + aBalloon);
        if (!this.isPoppable(aBalloon)) return false;
        this.unHighlight(aBalloon);
        ArrayList<Coord> myBalloons = new ArrayList<Coord>(20);
        myBalloons.addAll(this.gameBoard.likeColoredNeighborChain(aBalloon));
        myBalloons.add(aBalloon);
        this.score += myBalloons.size();
        this.coordList.addAll(myBalloons);
        this.action = "pop";
        this.gameBoard.popChain(myBalloons);
        this.fireGameEvent();
        this.gameBoard.squeezeAll();
        this.coordList.addAll(this.gameBoard.getGridAsList());
        this.action = "update";
        this.fireGameEvent();
        if (this.gameOver()){
            this.action = "gameover";
            this.fireGameEvent();
        }
        return true;
    }
    
    public void popAll(){
        ArrayList<Coord> moves = new ArrayList<Coord>(this.possibleMoves());
        this.gameBoard.popChain(moves);
        this.score += moves.size();
        this.coordList.addAll(moves);
        this.action = "pop";
        this.fireGameEvent();
        this.gameBoard.squeezeAll();
        this.coordList.addAll(this.gameBoard.getGridAsList());
        this.action = "update";
        this.fireGameEvent();
        if (this.gameOver()){
            this.action = "gameover";
            this.fireGameEvent();
        }
    }
    
    public void highlight(Coord aBalloon){
        if (!this.isPoppable(aBalloon)) return;
        //System.out.println("Highlighting " + aBalloon);
        this.coordList.addAll(this.gameBoard.likeColoredNeighborChain(aBalloon));
        this.coordList.add(aBalloon);
        this.action = "highlight";
        this.fireGameEvent();
    }
    
    public void unHighlight(Coord aBalloon){
        //if (!this.isPoppable(aBalloon)) return;
        this.coordList.addAll(this.gameBoard.likeColoredNeighborChain(aBalloon));
        this.coordList.add(aBalloon);
        this.action = "unhighlight";
        this.fireGameEvent();
    }
    
    public synchronized void addGameListener( GameListener listener ) {
        GameInterface.gameListeners.add( listener );
    }
    
    public synchronized void removeGameListener( GameListener listener ) {
        GameInterface.gameListeners.remove( listener );
    }
    
    public LinkedList<GameListener> getListeners(){
        return GameInterface.gameListeners;
    }
    
    private synchronized void fireGameEvent(){
        //System.out.println("Dispatching event " + this.action + " to " + GameInterface.gameListeners.size() + " listeners");
        GameEvent event = new GameEvent(this, this.coordList, this.action);
        for (GameListener t : GameInterface.gameListeners){
            t.gameEventReceived(event);
        }
        this.coordList.clear();
        this.action = "none";
    }
    
    public boolean equals(Object aGame){
        if ((aGame instanceof GameInterface)
                //&& (((GameInterface)aGame).getScore() == this.score)
                //&& (((GameInterface)aGame).getMaxScore() == GameInterface.maxScore)
                //&& (((GameInterface)aGame).getCoordList().equals(this.coordList))
                && (((GameInterface)aGame).gameBoard.equals(this.gameBoard))) return true;
                //&& (((GameInterface)aGame).getAction().equals(this.action))
                //&& (((GameInterface)aGame).getListeners().equals(GameInterface.gameListeners))) return true;
        return false;
    }
    
    public Object clone(){
//        GameInterface result = new GameInterface((GameGrid)this.gameBoard.clone(), GameInterface.gameListeners, this.score);
        GameInterface result = new GameInterface((GameGrid)this.gameBoard.clone(), this.score);
        return result;
    }
    
    public int compareTo(Object aGame){
        return this.gameBoard.compareTo(((GameInterface)aGame).gameBoard);
    }
    
    public String toString(){
        String result = "";
        result += "Gameboard is:\n" + this.gameBoard + "\n";
        return result;
    }
	
    private int score;
    private static int maxScore;
	private GameGrid gameBoard;
    private final ArrayList<Coord> coordList;
    private String action;
    private static LinkedList<GameListener> gameListeners;

    /**
     * @param args
     */
    public static void main(String[] args) {
        GameInterface test = new GameInterface(5,5);
		GameInterface testClone = (GameInterface)test.clone();
        System.out.println("Test is " + test);
        test.pop(test.possibleMoves().get(0));
        System.out.println("Test is " + test);
        System.out.println("TestClone is " + testClone);
        if (test.equals(testClone)){
            System.out.println("Equal");
        }else{
            System.out.println("Not equal");
        }
    }

}
