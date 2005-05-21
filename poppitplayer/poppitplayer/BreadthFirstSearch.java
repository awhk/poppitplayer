import java.util.*;

/**
 * 
 */

/**
 * @author Andrew
 *
 */
public class BreadthFirstSearch extends Search {

    BreadthFirstSearch(GameInterface aGame){
        this.node = new SearchNode(aGame);
        this.bestNode = null;
        this.bestScore = 0;
        this.solutionsFound = 0;
        this.unseenStates = new LinkedList<SearchNode>();
        this.seenNodes = new TreeSet<Balloon[]>(new BalloonArrayOrder());
        this.unseenNodes = new TreeSet<Balloon[]>(new BalloonArrayOrder());
        this.enqueueUnseen(this.node);
        this.storeUnseen(this.node);
        //this.totalNodes = 1;
    }
    
    /* (non-Javadoc)
     * @see Search#EnqueueUnseen(SearchNode)
     */
    @Override
    public void enqueueUnseen(SearchNode aNode) {
        this.unseenStates.offer(aNode);

    }

    /* (non-Javadoc)
     * @see Search#DequeueUnseen()
     */
    @Override
    public SearchNode dequeueUnseen() {
        return this.unseenStates.remove();
    }

    
    private Queue<SearchNode> unseenStates;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Search test = new BreadthFirstSearch(new GameInterface(7,7));
        test.search();
            System.out.println("Solution found in " + test.getNode().getDepth() + " moves.");
            test.playbackSolution();

    }

}
