import java.util.*;

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
public class GameInterface {
	
	public GameInterface(){
		this(15, 10);
	}
	
	public GameInterface(int aX, int aY){
		this.gameBoard = new GameGrid(aX, aY);
        this.action = "none";
        this.coordList = new ArrayList<Coord>();
        this.gameListeners = new LinkedList<GameListener>();
        this.maxScore = aX * aY;
        this.score = 0;
	}
	
	public ArrayList<Coord> possibleMoves(){
		return gameBoard.hasLikeColoredNeighbors();
	}
	
	public boolean isPoppable(Coord aBalloon){
        if (this.gameBoard.hasLikeColoredNeighbors(aBalloon)) return true;
		return false;
	}
    
    public boolean isPopped(Coord aBalloon){
        return this.gameBoard.getBalloon(aBalloon).isPopped();
    }
    
    public GameGrid getGrid(){
        return this.gameBoard;
    }
    
    public Balloon getBalloon(Coord aBalloon){
        return this.gameBoard.getBalloon(aBalloon);
    }
    
    public int getScore(){
        return this.score;
    }
    
    public int getMaxScore(){
        return this.maxScore;
    }
    
    public void resetGame(){
        this.gameBoard = new GameGrid(this.gameBoard.gridSize().getX()+1, this.gameBoard.gridSize().getY()+1);
        this.coordList.addAll(this.gameBoard.getGridAsList());
        //this.maxScore = this.gameBoard.gridSize().getX() * this.gameBoard.gridSize().getY();
        this.score = 0;
        this.action = "update";
        this.fireGameEvent();
    }
    
    public int valueOfMove(Coord aBalloon){
        if (!this.isPoppable(aBalloon)) return 0;
        ArrayList<Coord> myBalloons = new ArrayList<Coord>();
        myBalloons.addAll(this.gameBoard.likeColoredNeighborChain(aBalloon));
        myBalloons.add(aBalloon);
        return myBalloons.size();
    }
    
    public boolean gameOver(){
        if (this.possibleMoves().isEmpty()) return true;
        return false;
    }
    
    public boolean pop(Coord aBalloon){
        if (!this.isPoppable(aBalloon)) return false;
        this.unHighlight(aBalloon);
        ArrayList<Coord> myBalloons = new ArrayList<Coord>();
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
        this.gameListeners.add( listener );
    }
    
    public synchronized void removeGameListener( GameListener listener ) {
        this.gameListeners.remove( listener );
    }
    
    private synchronized void fireGameEvent(){
        //System.out.println("Dispatching event " + this.action + " to " + this.gameListeners.size() + " listeners");
        GameEvent event = new GameEvent(this, this.coordList, this.action);
        for (GameListener t : this.gameListeners){
            t.gameEventReceived(event);
        }
        this.coordList.clear();
        this.action = "none";
    }
	
    private int score;
    private final int maxScore;
	private GameGrid gameBoard;
    private ArrayList<Coord> coordList;
    private String action;
    private LinkedList<GameListener> gameListeners;

    /**
     * @param args
     */
    public static void main(String[] args) {
        GameInterface test = new GameInterface(5,5);
		for (Coord t : test.possibleMoves()){
			System.out.println(t);
		}
		System.out.print("(2,2) is poppable? ");
		System.out.println(test.isPoppable(new Coord(2,2)));
    }

}
