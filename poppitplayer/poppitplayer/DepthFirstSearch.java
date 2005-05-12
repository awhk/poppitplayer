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
//        this.seenStates = new Stack<SearchNode>();
        this.seenNodes = new TreeSet<Integer>();
        this.unseenNodes = new TreeSet<Integer>();
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

//    /* (non-Javadoc)
//     * @see Search#EnqueueSeen(SearchNode)
//     */
//    @Override
//    public void EnqueueSeen(SearchNode aNode) {
//        this.seenStates.push(aNode);
//
//    }

    /* (non-Javadoc)
     * @see Search#DequeueUnseen()
     */
    @Override
    public SearchNode dequeueUnseen() {
        return this.unseenStates.pop();
    }

//    /* (non-Javadoc)
//     * @see Search#DequeueSeen()
//     */
//    @Override
//    public SearchNode DequeueSeen() {
//        return this.seenStates.pop();
//    }

//    /* (non-Javadoc)
//     * @see Search#SeenEmpty()
//     */
//    @Override
//    public boolean SeenEmpty() {
//        return this.seenStates.empty();
//    }

//    /* (non-Javadoc)
//     * @see Search#UnseenEmpty()
//     */
//    @Override
//    public boolean UnseenEmpty() {
//        return this.unseenStates.empty();
//    }

//    /* (non-Javadoc)
//     * @see Search#UnseenSize()
//     */
//    @Override
//    public int UnseenSize() {
//        return this.unseenStates.size();
//    }

//    /* (non-Javadoc)
//     * @see Search#SeenSize()
//     */
//    @Override
//    public int SeenSize() {
//        return this.seenStates.size();
//    }

//    /* (non-Javadoc)
//     * @see Search#SeenContains(SearchNode)
//     */
//    @Override
//    public boolean SeenContains(SearchNode aNode) {
//        return this.seenStates.contains(aNode);
//    }
    
//    /* (non-Javadoc)
//     * @see Search#UnseenContains(SearchNode)
//     */
//    @Override
//    public boolean UnseenContains(SearchNode aNode) {
//        return this.unseenStates.contains(aNode);
//    }

    private Stack<SearchNode> unseenStates;
//    private Stack<SearchNode> seenStates;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Search test = new DepthFirstSearch(new GameInterface(7,7));
        test.search();
        if (test.solutionFound()){
            System.out.println("Solution found in " + test.getNode().getDepth() + " moves.");
            test.playbackSolution();
        }else{
            System.out.println("No solution found.");
        }

    }

}
