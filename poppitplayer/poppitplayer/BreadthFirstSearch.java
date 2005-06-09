import java.util.*;

/**
 * 
 */

/**
 * @author Andrew
 * 
 */
public class BreadthFirstSearch extends Search {

    BreadthFirstSearch(GameInterface aGame) {
        this.node = new SearchNode(aGame);
        this.unseenStates = new LinkedList<SearchNode>();
        // this.enqueueUnseen(this.node);
        this.storeUnseen(this.node);
    }

    /*
     * (non-Javadoc)
     * 
     * @see Search#EnqueueUnseen(SearchNode)
     */
    @Override
    public void enqueueUnseen(SearchNode aNode) {
        if (!(this.unseenStates.offer(aNode))) {
            System.out.println("Insertion into queue failed!");
            System.exit(0);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see Search#DequeueUnseen()
     */
    @Override
    public SearchNode dequeueUnseen() {
        return this.unseenStates.remove();
    }

    public int queueSize() {
        return this.unseenStates.size();
    }

    private Queue<SearchNode> unseenStates;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Search test = new BreadthFirstSearch(new GameInterface(7, 7));
        test.search();
        test.playbackSolution();

    }

}
