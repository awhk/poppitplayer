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
        this.solutionFound = false;
        this.unseenStates = new LinkedList<SearchNode>();
        this.seenStates = new LinkedList<SearchNode>();
        this.EnqueueUnseen(this.node);
    }
    
    /* (non-Javadoc)
     * @see Search#EnqueueUnseen(SearchNode)
     */
    @Override
    public void EnqueueUnseen(SearchNode aNode) {
        this.unseenStates.offer(aNode);

    }

    /* (non-Javadoc)
     * @see Search#EnqueueSeen(SearchNode)
     */
    @Override
    public void EnqueueSeen(SearchNode aNode) {
        this.seenStates.offer(aNode);

    }

    /* (non-Javadoc)
     * @see Search#DequeueUnseen()
     */
    @Override
    public SearchNode DequeueUnseen() {
        return this.unseenStates.remove();
    }

    /* (non-Javadoc)
     * @see Search#DequeueSeen()
     */
    @Override
    public SearchNode DequeueSeen() {
        return this.seenStates.remove();
    }

    /* (non-Javadoc)
     * @see Search#SeenEmpty()
     */
    @Override
    public boolean SeenEmpty() {
        return this.seenStates.isEmpty();
    }

    /* (non-Javadoc)
     * @see Search#UnseenEmpty()
     */
    @Override
    public boolean UnseenEmpty() {
        return this.unseenStates.isEmpty();
    }

    /* (non-Javadoc)
     * @see Search#UnseenSize()
     */
    @Override
    public int UnseenSize() {
        return this.unseenStates.size();
    }

    /* (non-Javadoc)
     * @see Search#SeenSize()
     */
    @Override
    public int SeenSize() {
        return this.seenStates.size();
    }

    /* (non-Javadoc)
     * @see Search#SeenContains(SearchNode)
     */
    @Override
    public boolean SeenContains(SearchNode aNode) {
        return this.seenStates.contains(aNode);
    }
    
    /* (non-Javadoc)
     * @see Search#UnseenContains(SearchNode)
     */
    @Override
    public boolean UnseenContains(SearchNode aNode) {
        return this.unseenStates.contains(aNode);
    }

    
    private Queue<SearchNode> unseenStates;
    private Queue<SearchNode> seenStates;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Search test = new BreadthFirstSearch(new GameInterface(7,7));
        test.search();
        if (test.solutionFound()){
            System.out.println("Solution found in " + test.getNode().getDepth() + " moves.");
            test.playbackSolution();
        }else{
            System.out.println("No solution found.");
        }
    }

}
