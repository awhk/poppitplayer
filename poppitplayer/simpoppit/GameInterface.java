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
	}
	
	public ArrayList<Coord> possibleMoves(){
		return gameBoard.hasLikeColoredNeighbors();
	}
	
	public boolean isPoppable(Coord aBalloon){
		//if (this.possibleMoves().contains(aBalloon)) return true;
        if (this.gameBoard.hasLikeColoredNeighbors(aBalloon)) return true;
		return false;
	}
    
    public GameGrid getGrid(){
        return this.gameBoard;
    }
    
    public Balloon getBalloon(Coord aBalloon){
        return this.gameBoard.getBalloon(aBalloon);
    }
    
    public boolean pop(Coord aBalloon){
        if (!this.isPoppable(aBalloon)) return false;
        this.unHighlight(aBalloon);
        ArrayList<Coord> myBalloons = new ArrayList<Coord>();
        myBalloons.addAll(this.gameBoard.likeColoredNeighborChain(aBalloon));
        myBalloons.add(aBalloon);
        this.coordList.addAll(myBalloons);
        this.action = "pop";
        this.gameBoard.popChain(myBalloons);
        this.fireGameEvent();
        this.gameBoard.squeezeAll();
        this.coordList.addAll(this.gameBoard.getGridAsList());
        this.action = "update";
        this.fireGameEvent();
        return true;
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
            if (!this.coordList.contains(t.getCoord())) continue;
            t.gameEventReceived(event);
        }
        this.coordList.clear();
        this.action = "none";
    }
	
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
