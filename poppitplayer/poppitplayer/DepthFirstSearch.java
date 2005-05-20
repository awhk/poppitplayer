import java.util.*;

/**
 * 
 */

/**
 * @author Andrew
 *
 */
public class DepthFirstSearch extends Search {

    DepthFirstSearch(GameInterface aGame){
        this.node = new SearchNode(aGame);
        this.bestNode = null;
        this.bestScore = 0;
        this.solutionsFound = 0;
        this.solutionFound = false;
        this.unseenStates = new Stack<SearchNode>();
        this.seenNodes = new TreeSet<Balloon[]>(new BalloonArrayOrder());
        this.unseenNodes = new TreeSet<Balloon[]>(new BalloonArrayOrder());
        this.enqueueUnseen(this.node);
        this.storeUnseen(this.node);
        this.totalNodes = 1;
    }
    
    /* (non-Javadoc)
     * @see Search#EnqueueUnseen(SearchNode)
     */
    @Override
    public void enqueueUnseen(SearchNode aNode) {
        this.unseenStates.push(aNode);

    }

    /* (non-Javadoc)
     * @see Search#DequeueUnseen()
     */
    @Override
    public SearchNode dequeueUnseen() {
        return this.unseenStates.pop();
    }

    private Stack<SearchNode> unseenStates;

    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Search test = new DepthFirstSearch(new GameInterface());
        test.search();
        if (test.solutionFound()){
            System.out.println("Solution found in " + test.getNode().getDepth() + " moves.");
            test.playbackSolution();
        }else{
            System.out.println("No solution found.");
        }

    }

}
