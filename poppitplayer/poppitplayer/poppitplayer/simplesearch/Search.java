package poppitplayer.simplesearch;

import java.util.*;
import simpoppit.gameboard.*;
//import org.apache.log4j.Logger;
import simpoppit.gui.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * <p>
 * Title: Search
 * </p>
 * 
 * <p>
 * Description: This is the search "superclass" that is extended in order to
 * implement various kinds of searches. This class implements all the
 * functionality of a search except for the mechanism to choose the next node to
 * assess (either expand to new unexplored nodes if possible, or terminate the
 * search if it is a solution node). For instance, to implement a depth first
 * search, this class would be extended and a stack would be utilized to store
 * unexplored nodes. This class maintains an independent store of seen and
 * unseen nodes in the form of a {@link java.util.TreeSet} to facilitate rapid
 * detection and elimination of duplicate nodes, as such detection would not be
 * efficient with stack or queue data structures.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Andrew W. Henry
 * @version 1.0
 * @see SearchNode
 * 
 */
public abstract class Search {

    /**
     * If the current {@link SearchNode} represents a goal state (i.e. it cannot
     * be expanded into deeper child nodes), return true. Otherwise, return
     * false.
     * 
     * @return boolean
     */
    public boolean goalState() {
        // If the current node is invalid, then it sure isn't a goal state
        if (this.node == null)
            return false;
        return this.node.getState().isGameOver();
    }

    /**
     * Return the score of the current node. Basically, how many balloons have
     * been popped.
     * 
     * @return integer score of current node
     */
    public int nodeScore() {
        return this.node.getState().getScore();
    }

    /**
     * If another node is stored and waiting to be examined, remove it from
     * storage and assign it to the current node. If no nodes are stored, then
     * the search space for this game must be exhausted.
     */
    public void nextNode() {
        // If no unvisited nodes are stored, set the current node to null
        if (this.unseenEmpty())
            this.node = null;
        // Otherwise, take the next waiting node out of storage and make it the
        // current node
        else {
            // Assign current node to next available unseen node and drop that
            // node from the unseen store
            this.node = this.dequeueUnseen();
            // Drop the corresponding node from the local unseen node set
            this.unseenDrop(this.node);
        }
    }

    /**
     * Given that the current node is valid, determine if that node has
     * successor nodes (i.e. represents a game state that has unplayed moves),
     * and if so, generate those successors. Add each successor to the "unseen
     * node" store if it is not already there and has not been seen previously.
     */
    public void expand() {
        // Query the current node for any possible successor nodes, and iterate
        // over that set
        for (SearchNode t : this.node.successors()) {
            // If this node has already been explored, skip it and keep track of
            // that fact
            if (this.seenContains(t)) {
                this.skippedBecauseSeen++;
                continue;
            }
            // If this node has already been queued for later exploration, skip
            // it and keep track of that fact
            if (this.unseenContains(t)) {
                this.skippedBecauseUnseen++;
                continue;
            }
            // Otherwise, we have a unique node, so store it away for future
            // exploration
            this.storeUnseen(t);
            
            // Update the node count for whatever depth this node represents
            this.depthTrack[t.getDepth()] = this.depthTrack[t.getDepth()] + 1;
            // if (this.unseenNodes.size() != this.queueSize()) {
            // System.out.println("Uh-oh, unseen queues out of sync!");
            // System.exit(0);
            // }
        }
    }

    /**
     * This is the primary method of this class, and it initiates and propogates
     * a search, proceeding through the search space of the game until it has
     * either exhausted every possible node/game state, or has found a perfect
     * solution (as there is no possibility to improve on a solution that pops
     * all balloons since number of moves is not a factor in Poppit).
     */
    public void search() {
        // Count the number of times we go through the main loop between finding
        // solutions
        int loopCount = 0;
        // As long as we haven't run out of candidate nodes, keep searching
        while (!(this.unseenEmpty())) {
            // Increment the loop counter/number of nodes examined
            loopCount++;
            // Output: print a dot for every node examined
            System.out.print(".");
            // Output: every 80th dot, print a brief summary and a CR
            if (loopCount % 80 == 0)
                System.out.println("(" + loopCount + " nodes)");
            // Output: every 800th dot, print a detailed summary
            if (loopCount % 800 == 0) {
                System.out.println("Total nodes currently in storage: "
                        + ((this.unseenNodes.size()) + this.seenNodes.size())
                        + " [" + this.unseenNodes.size() + " (unexplored), "
                        + this.seenNodes.size() + " (explored)]");
                System.out.println("Nodes by depth:\nDepth\t:\tNodes\t:\tMultiple of previous depth");
                for (int i = 0; i < this.depthTrack.length; i++) {
                    if (this.depthTrack[i] == 0) continue;
                    System.out.print(i + "\t:\t" + this.depthTrack[i] + "\t:\t");
                    if (this.depthTrack[i-1] == 0){
                        System.out.println("-");
                    } else {
                        System.out.println(this.depthTrack[i] / this.depthTrack[i-1]);
                    }
                }
            }
            // If we found a solution node, do something about it
            if (this.goalState()) {
                // Output: print a summary of the details of the solution found
                System.out.println("\nFound solution after examining "
                        + loopCount + " nodes.");
                System.out.println("Score of solution found is "
                        + this.node.getState().getScore());
                System.out.println("Max score possible for this game is "
                        + this.node.getState().getMaxScore());
                System.out.println("Tested " + this.seenSize()
                        + " nodes so far.");
                System.out.println("Found " + this.solutionsFound
                        + " solutions so far.");
                System.out.println(this.unseenSize()
                        + " nodes remain in the current queue.");
                // Keep track of the number of solutions found so far (there can
                // be many, many solutions to any given Poppit game)
                this.solutionsFound++;
                // If the solution we just found is better than our previous
                // best solution, make a note of it. This way we if we end up
                // exhausting the search space, we can go back and know that the
                // best (non-perfect) solution we ended up finding along the way
                // is the best possible solution for the starting board layout.
                if ((this.node.getState().getScore() > this.bestScore)) {
                    this.bestScore = this.node.getState().getScore();
                    this.bestNode = this.node;
                    // Output: note that we have a new "best game" candidate
                    System.out.println("Setting score to " + this.bestScore);
                    System.out.println("Setting best node to " + this.bestNode);
                    // Check and see if this new best game is in fact a perfect
                    // game. If it is, we do not need to continue searching as
                    // perfect games cannot be bested, so we should break out of
                    // our search loop now.
                    if ((this.bestNode.getState().getScore() >= this.bestNode
                            .getState().getMaxScore())) {
                        System.out
                                .println("Found perfect game, abandoning search.");
                        this.perfect = true;
                        break;
                    }
                }
                // If we get here then we found a solution, but not a perfect
                // one, so clear our loop/node counter and begin searching again
                loopCount = 0;
                // A break here would stop after finding any solution
                // break;
            }
            // Store the current node in our "visited nodes" store
            this.storeSeen(this.node);
            // If the current node is a solution node, then it cannot be
            // expanded because there would by definition be no more moves left.
            // So check first to see if the current node is a solution node.
            if (!(this.goalState())) {
                // If this is not a solution node then expand it
                this.expand();
            }
            // Get the next available search node
            this.nextNode();
        }
        // If we get here, we have exhausted the search space, so we will output
        // a little summary noting that along with details about the best
        // solution we did find.
        if ((!this.perfect) && (this.bestNode != null)) {
            System.out.println("\nFound solution after examining " + loopCount
                    + " nodes.");
            System.out.println("Score of solution found is "
                    + this.bestNode.getState().getScore());
            System.out.println("Max score possible for this game is "
                    + this.bestNode.getState().getMaxScore());
            System.out.println("Tested " + this.seenSize() + " nodes so far.");
            System.out.println("Found " + this.solutionsFound
                    + " solutions so far.");
            System.out.println(this.unseenSize()
                    + " nodes remain in the current queue.");
            System.out
                    .println("Exhausted search space, no perfect game found for this board.");
        }
    }

    /**
     * This method will play back the game solution contained in the bestNode
     * variable (i.e. the best solution discovered by the search method). This
     * method presumes that the search method has been run prior to this. This
     * method employs the human-player gui interface for output.
     */
    public void playbackSolution() {
        // If the bestNode variable is undefined, something is really wrong so
        // abort the playback
        if (this.bestNode == null)
            return;
        // First, initialize a "node pointer" to the ultimate/solution node
        SearchNode myNode = this.bestNode;
        // Initialize a stack to hold the move list. Moves will be extracted
        // from the solution sequence in reverse order, so a stack will allow
        // them to be popped off and the game replayed from start to finish.
        Stack<Coord> moves = new Stack<Coord>();
        // Iterate through the nodes of the solution set, starting from the last
        // node and ending at the first.
        while (myNode.getAncestor() != null) {
            // Retrieve the operator from the current search node (i.e. the move
            // that resulted in the game state of the current node)
            moves.push(myNode.getOperator());
            // Prime the next node up in the game solution sequence for
            // examination
            myNode = myNode.getAncestor();
        }
        // Initialize the gui with a COPY of the starting game state, so the
        // replay can be run more than once. Start the gui in non-interactive
        // mode.
        SimPoppitGui gui = new SimPoppitGui((GameInterface) myNode.getState()
                .clone(), false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        // Put up a dialog box to allow the start of the playback to be delayed
        // until the viewer is ready
        JOptionPane.showMessageDialog(gui, "Click OK to watch playback",
                "Game Solved", JOptionPane.DEFAULT_OPTION);
        // Iterate through the move stack
        while (!(moves.isEmpty())) {
            // Highlight the impending move...
            gui.getGame().highlight(moves.peek());
            // ...for one second...
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Failed to sleep - " + e);
            }
            // ...then make the move.
            gui.getGame().pop(moves.pop());
        }
        // When the playback is complete, offer the option to watch it again
        int selection = JOptionPane.showConfirmDialog(gui,
                "Click OK to watch playback again or cancel not to",
                "Game Solved", JOptionPane.OK_CANCEL_OPTION);
        // If the user does not want to see it again, just wait, displaying the
        // solution game layout
        if (selection == JOptionPane.CANCEL_OPTION) {
        } else {
            // Otherwise, annihilate the existing gui instance
            gui.setVisible(false);
            gui.dispose();
            gui = null;
            // And start the playback process from scratch
            this.playbackSolution();
        }
    }

    /**
     * Return the total number of solutions found so far by the current search
     * 
     * @return integer number of solutions found
     */
    public int getSolutionTotal() {
        return this.solutionsFound;
    }

    /**
     * Return the score of the best solution found so far by the current search.
     * 
     * @return integer score of best solution so far
     */
    public int getBestScore() {
        return this.bestScore;
    }

    /**
     * Return the number of nodes that were generated as successors to some node
     * under consideration that were discarded because they duplicated nodes
     * that had previously been examined. I.e., this is the number of game
     * states encountered so far that are basically a different way to get to
     * the same place, and we have already gotten to that place once already.
     * 
     * @return integer number of nodes discarded due to previous node
     *         duplication
     */
    public int getSkippedSeen() {
        return this.skippedBecauseSeen;
    }

    /**
     * Return the number of nodes that were generated as successors to some node
     * under consideration that were discarded because they duplicated nodes
     * that were already scheduled to be examined.
     * 
     * @return integer number of nodes discarded due to pending node duplication
     */
    public int getSkippedUnseen() {
        return this.skippedBecauseUnseen;
    }

    /**
     * Add a {@link SearchNode} to the store of nodes that have been visited.
     * 
     * @param aNode
     *            a visited (seen) node
     */
    public void storeSeen(SearchNode aNode) {
        this.seenNodes.add(aNode);
    }

    /**
     * Return the number of nodes contained in the visited/seen nodes store.
     * 
     * @return integer number of nodes already visited
     */
    public int seenSize() {
        return this.seenNodes.size();
    }

    /**
     * Return true if the given node already exists in the store of nodes that
     * have been seen/visited.
     * 
     * @param aNode
     *            a node to test for inclusion in the "visited" store
     * @return boolean
     */
    public boolean seenContains(SearchNode aNode) {
        return this.seenNodes.contains(aNode);
    }

    /**
     * Add the given node both to the local store of unseen/unvisited nodes as
     * well as the implementation-specific store (i.e. the store that defines
     * the behavior of the search).
     * 
     * @param aNode
     *            the unseen/unvisited node to store
     */
    public void storeUnseen(SearchNode aNode) {
        // Add the node to the local store
        this.unseenNodes.add(aNode);
        // Add the node to the implementation-specific store
        enqueueUnseen(aNode);
    }

    /**
     * Return true if the store of unseen/unvisted nodes is empty, false
     * otherwise.
     * 
     * @return boolean
     */
    public boolean unseenEmpty() {
        return this.unseenNodes.isEmpty();
    }

    /**
     * Return the integer number of nodes that have been generated so far by
     * {@link #expand()}ing other nodes, but that have not yet been visited
     * themselves.
     * 
     * @return number of nodes generated but not visited yet
     */
    public int unseenSize() {
        return this.unseenNodes.size();
    }

    /**
     * Return true if the given node already exists in the store of nodes that
     * have not yet been seen/visited.
     * 
     * @param aNode
     *            a node to test for inclusion in the unseen/unvisited store
     * @return boolean
     */
    public boolean unseenContains(SearchNode aNode) {
        return this.unseenNodes.contains(aNode);
    }

    /**
     * Remove the given node from the local store of unseen/unvisited nodes.
     * This is done whenever a node is removed fromt he implementation-dependent
     * store to keep the two stores synchronized.
     * 
     * @param aNode
     *            the node to remove fromt he local store
     */
    public void unseenDrop(SearchNode aNode) {
        this.unseenNodes.remove(aNode);
    }

    /**
     * Add a node to the implementation-dependent store. The nature of the
     * implementation of this method will affect the type of search that is
     * ultimately performed by this class.
     * 
     * @param aNode
     *            an unseen/unvisited node to be stored for future analysis
     */
    abstract public void enqueueUnseen(SearchNode aNode);

    /**
     * Remove a node from the implementation-dependent store. The nature of the
     * implementation of this method will affect the type of search that is
     * ultimately performed by this class.
     * 
     * @return the "next" node to be tested as determined by the implementor of
     *         this method
     */
    abstract public SearchNode dequeueUnseen();

    /**
     * Returns the number of nodes currently in the implementation-dependent
     * store.
     * 
     * @return integer number of unseen nodes currently in storage
     */
    abstract public int queueSize();

    /**
     * Node currently being examined
     */
    protected SearchNode node;

    /**
     * Node representing best game solution so far
     */
    protected SearchNode bestNode = null;

    /**
     * Score of best game so far
     */
    protected int bestScore = 0;

    /**
     * Number of solutions found so far
     */
    protected int solutionsFound = 0;

    /**
     * Number of nodes skipped because they duplicate visited nodes
     */
    protected int skippedBecauseSeen = 0;

    /**
     * Number of nodes skipped because they duplicate unvisited nodes
     */
    protected int skippedBecauseUnseen = 0;

    /**
     * Boolean switch to short-circuit search if a perfect game is found
     */
    protected boolean perfect = false;

    /**
     * Local storage for visited nodes
     */
    protected TreeSet<SearchNode> seenNodes = new TreeSet<SearchNode>();

    /**
     * Local storage for unvisited nodes
     */
    protected TreeSet<SearchNode> unseenNodes = new TreeSet<SearchNode>();

    /**
     * Array to track the number of nodes generated for each depth level
     */
    protected int[] depthTrack = new int[500];

    // public static Logger log = Logger.getLogger(Search.class);

    public static void main(String[] args) {
        GameInterface game = new GameInterface(10, 10);
        BreadthFirstSearch bfs = new BreadthFirstSearch((GameInterface) game
                .clone());
        DepthFirstSearch dfs = new DepthFirstSearch((GameInterface) game
                .clone());
        // dfs.search();
        bfs.search();
        // System.out.println("DFS searched " + dfs.seenSize()
        // + " nodes total, with " + dfs.unseenSize() + " unexplored.");
        // System.out.println("DFS skipped " + dfs.getSkippedSeen()
        // + " nodes because it already explored them, and "
        // + dfs.getSkippedUnseen()
        // + " because they were already queued to explore.");
        // System.out.println("DFS found " + dfs.getSolutionTotal()
        // + " solutions, with the best solution having a score of "
        // + dfs.getBestScore());
        // dfs.playbackSolution();
        System.out.println("BFS searched " + bfs.seenSize()
                + " nodes total, with " + bfs.unseenSize() + " unexplored.");
        System.out.println("BFS skipped " + bfs.getSkippedSeen()
                + " nodes because it already explored them, and "
                + bfs.getSkippedUnseen()
                + " because they were already queued to explore.");
        System.out.println("BFS found " + bfs.getSolutionTotal()
                + " solutions, with the best solution having a score of "
                + bfs.getBestScore());
        bfs.playbackSolution();
    }

}
