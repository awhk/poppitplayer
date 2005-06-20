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
            if (loopCount % 800 == 0)
                System.out.println("Total nodes currently in storage: "
                        + ((this.unseenNodes.size() * 2) + this.seenNodes
                                .size()) + " [" + this.unseenNodes.size()
                        + " (unexplored) x2," + this.seenNodes.size()
                        + " (explored)]");
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
                // Since we found a solution, but not a perfect one, clear our
                // loop/node counter and begin searching again
                loopCount = 0;
//                break;
            }
            this.storeSeen(this.node);
            if (!(this.goalState())) {
                this.expand();
            }
            this.nextNode();
        }
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

    public void playbackSolution() {
        // s_runtime.runFinalization();
        // s_runtime.gc();
        // Thread.yield();
        SearchNode myNode = this.bestNode;
        // SearchNode myNode = this.node;
        Stack<Coord> moves = new Stack<Coord>();
        // GameInterface myGame = gui.getGame();
        while (myNode.getAncestor() != null) {
            moves.push(myNode.getOperator());
            myNode = myNode.getAncestor();
        }
        SimPoppitGui gui = new SimPoppitGui((GameInterface) myNode.getState()
                .clone(), false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        // System.out.println("Number of moves is " + moves.size());
        JOptionPane.showMessageDialog(gui, "Click OK to watch playback",
                "Game Solved", JOptionPane.DEFAULT_OPTION);
        while (!(moves.isEmpty())) {
            gui.getGame().highlight(moves.peek());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Failed to sleep - " + e);
            }
            gui.getGame().pop(moves.pop());
        }
        int selection = JOptionPane.showConfirmDialog(gui,
                "Click OK to watch playback again or cancel not to",
                "Game Solved", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.CANCEL_OPTION) {
            // System.exit(0);
        } else {
            gui.setVisible(false);
            gui.dispose();
            gui = null;
            this.playbackSolution();
        }
    }

    public int getSolutionTotal() {
        return this.solutionsFound;
    }

    public int getBestScore() {
        return this.bestScore;
    }

    public int getSkippedSeen() {
        return this.skippedBecauseSeen;
    }

    public int getSkippedUnseen() {
        return this.skippedBecauseUnseen;
    }

    public void storeSeen(SearchNode aNode) {
        this.seenNodes.add(aNode);
    }

    public boolean seenEmpty() {
        return this.seenNodes.isEmpty();
    }

    public int seenSize() {
        return this.seenNodes.size();
    }

    public boolean seenContains(SearchNode aNode) {
        return this.seenNodes.contains(aNode);
    }

    public void storeUnseen(SearchNode aNode) {
        this.unseenNodes.add(aNode);
        this.enqueueUnseen(aNode);
    }

    public boolean unseenEmpty() {
        return this.unseenNodes.isEmpty();
    }

    public int unseenSize() {
        return this.unseenNodes.size();
    }

    public boolean unseenContains(SearchNode aNode) {
        return this.unseenNodes.contains(aNode);
    }

    public void unseenDrop(SearchNode aNode) {
        this.unseenNodes.remove(aNode);
    }

    abstract public void enqueueUnseen(SearchNode aNode);

    abstract public SearchNode dequeueUnseen();

    abstract public int queueSize();

    protected SearchNode node;

    protected SearchNode bestNode = null;

    protected int bestScore = 0;

    protected int solutionsFound = 0;

    protected int skippedBecauseSeen = 0;

    protected int skippedBecauseUnseen = 0;

    protected boolean perfect = false;

    protected TreeSet<SearchNode> seenNodes = new TreeSet<SearchNode>();

    protected TreeSet<SearchNode> unseenNodes = new TreeSet<SearchNode>();

    // public static Logger log = Logger.getLogger(Search.class);

    // private static final Runtime s_runtime = Runtime.getRuntime();

    public static void main(String[] args) {
        GameInterface game = new GameInterface(10, 10);
        BreadthFirstSearch bfs = new BreadthFirstSearch((GameInterface) game
                .clone());
        DepthFirstSearch dfs = new DepthFirstSearch((GameInterface) game
                .clone());
        dfs.search();
        bfs.search();
        System.out.println("DFS searched " + dfs.seenSize()
                + " nodes total, with " + dfs.unseenSize() + " unexplored.");
        System.out.println("DFS skipped " + dfs.getSkippedSeen()
                + " nodes because it already explored them, and "
                + dfs.getSkippedUnseen()
                + " because they were already queued to explore.");
        System.out.println("DFS found " + dfs.getSolutionTotal()
                + " solutions, with the best solution having a score of "
                + dfs.getBestScore());
        dfs.playbackSolution();
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
