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
		this.location = new Coord(0,0);
		this.gameBoard = new GameGrid();
	}
	
	public GameInterface(int aX, int aY){
		this.location = new Coord(0,0);
		this.gameBoard = new GameGrid(aX, aY);
	}
	
	public ArrayList<Coord> possibleMoves(){
		return gameBoard.hasLikeColoredNeighbors();
	}
	
	public boolean isPoppable(Coord aBalloon){
		if (this.possibleMoves().contains(aBalloon)) return true;
		return false;
	}
	
	class Move {
		
		public Move(){
			Locations = GameInterface.this.gameBoard.getGridAsList();
		}
		
		
		private MoveType moveType;
		private ArrayList<Coord> Locations;
		private ArrayList<String> Test;
		private ArrayList<String> Action;
		private static enum MoveType{test, action};
	}
	
	
	private Coord location;
	private GameGrid gameBoard;

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
