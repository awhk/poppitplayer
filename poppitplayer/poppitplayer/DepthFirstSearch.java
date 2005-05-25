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
        this.unseenStates = new Stack<SearchNode>();
        //this.seenNodes = new TreeSet<Balloon[]>(new BalloonArrayOrder());
        //this.unseenNodes = new TreeSet<Balloon[]>(new BalloonArrayOrder());
        this.seenNodes = new TreeSet<BalloonInt>();
        this.unseenNodes = new TreeSet<BalloonInt>();
        this.enqueueUnseen(this.node);
        this.storeUnseen(this.node);
        this.perfect = false;
        //this.totalNodes = 1;
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
    
    public int queueSize(){
        return this.unseenStates.size();
    }


    private Stack<SearchNode> unseenStates;

    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Search test = new DepthFirstSearch(new GameInterface(5,5));
        test.search();
        test.playbackSolution();


    }

}
