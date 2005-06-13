package poppitplayer.simplesearch;

import java.util.*;
import simpoppit.gameboard.*;
//import org.apache.log4j.Logger;
import simpoppit.gui.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author Andrew
 * 
 */
public abstract class Search {

    public SearchNode getNode() {
        return this.node;
    }

    public boolean goalState() {
        if (this.node == null)
            return false;
        return this.node.getState().gameOver();
    }

    public int nodeScore() {
        return this.node.getState().getScore();
    }

    public void nextNode() {
        if (this.unseenEmpty())
            this.node = null;
        else {
            this.node = this.dequeueUnseen();
            this.unseenDrop(this.node);
        }
    }

    public void expand() {
        //System.out.println("Queuing " + this.node.successors().size() + " nodes.");
        //int beforeSize = this.unseenSize();
        for (SearchNode t : this.node.successors()) {
            if (this.seenContains(t)) {
                this.skippedBecauseSeen++;
                continue;
            }
            if (this.unseenContains(t)) {
                this.skippedBecauseUnseen++;
                continue;
            }
            this.storeUnseen(t);
            if (this.unseenNodes.size() != this.queueSize()) {
                System.out.println("Uh-oh, unseen queues out of sync!");
                System.exit(0);
            }
        }
        // this.totalNodes += (this.unseenSize() - beforeSize);
        //System.out.println("(Actually queued " + (this.unseenSize() - beforeSize)+ " nodes)");
        // System.out.println("Generated " + this.totalNodes + " total nodes so
        // far.");
    }

    public void search() {
        int loopCount = 0;
        while (!(this.unseenEmpty())) {
            loopCount++;
            System.out.print(".");
            if (loopCount % 80 == 0)
                System.out.println("(" + loopCount + " nodes)");
            if (this.goalState()) {
                System.out.println("\nFound solution after examining " + loopCount
                        + " nodes.");
                // System.out.println("Found a solution!");
                System.out.println("Score of solution found is "
                        + this.node.getState().getScore());
                System.out.println("Max score possible for this game is "
                        + this.node.getState().getMaxScore());
                System.out.println("Tested " + this.seenSize() + " nodes so far.");
                System.out.println("Found " + this.solutionsFound
                                + " solutions so far.");
                System.out.println(this.unseenSize()
                        + " nodes remain in the current queue.");
                this.solutionsFound++;
                if ((this.node.getState().getScore() > this.bestScore)) {
                    this.bestScore = this.node.getState().getScore();
                    this.bestNode = this.node;
                    System.out.println("Setting score to " + this.bestScore);
                    System.out.println("Setting best node to " + this.bestNode);
                    if ((this.bestNode.getState().getScore() == this.bestNode
                            .getState().getMaxScore())) {
                        System.out.println("Found perfect game, abandoning search.");
                        this.perfect = true;
                        break;
                    }
                }
                loopCount = 0;
                // break;
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
            System.out.println("Found " + this.solutionsFound + " solutions so far.");
            System.out.println(this.unseenSize()
                            + " nodes remain in the current queue.");
            System.out.println("Exhausted search space, no perfect game found for this board.");
        }
    }

    public void playbackSolution() {
        s_runtime.runFinalization();
        s_runtime.gc();
        Thread.yield();
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
        this.seenNodes.add(aNode.getState().getGrid());
    }

    public boolean seenEmpty() {
        return this.seenNodes.isEmpty();
    }

    public int seenSize() {
        return this.seenNodes.size();
    }

    public boolean seenContains(SearchNode aNode) {
        return this.seenNodes.contains(aNode.getState().getGrid());
    }

    public void storeUnseen(SearchNode aNode) {
        this.unseenNodes.add(aNode.getState().getGrid());
        this.enqueueUnseen(aNode);
    }

    public boolean unseenEmpty() {
        return this.unseenNodes.isEmpty();
    }

    public int unseenSize() {
        return this.unseenNodes.size();
    }

    public boolean unseenContains(SearchNode aNode) {
        return this.unseenNodes.contains(aNode.getState().getGrid());
    }

    public void unseenDrop(SearchNode aNode) {
        this.unseenNodes.remove(aNode.getState().getGrid());
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

    // protected int totalNodes;
    protected boolean perfect = false;

    protected TreeSet<GameGrid> seenNodes = new TreeSet<GameGrid>();

    protected TreeSet<GameGrid> unseenNodes = new TreeSet<GameGrid>();

//    public static Logger log = Logger.getLogger(Search.class);

    private static final Runtime s_runtime = Runtime.getRuntime();

    public static void main(String[] args) {
        GameInterface game = new GameInterface(8, 8);
        BreadthFirstSearch bfs = new BreadthFirstSearch((GameInterface) game
                .clone());
        DepthFirstSearch dfs = new DepthFirstSearch((GameInterface) game
                .clone());
//        dfs.search();
        bfs.search();
//        System.out.println("DFS searched " + dfs.seenSize()
//                + " nodes total, with " + dfs.unseenSize() + " unexplored.");
//        System.out.println("DFS skipped " + dfs.getSkippedSeen()
//                + " nodes because it already explored them, and "
//                + dfs.getSkippedUnseen()
//                + " because they were already queued to explore.");
//        System.out.println("DFS found " + dfs.getSolutionTotal()
//                + " solutions, with the best solution having a score of "
//                + dfs.getBestScore());
//        dfs.playbackSolution();
        System.out.println("BFS searched " + bfs.seenSize()
                + " nodes total, with " + bfs.unseenSize() + " unexplored.");
        System.out.println("BFS skipped " + bfs.getSkippedSeen()
                + " nodes because it already explored them, and "
                + bfs.getSkippedUnseen()
                + " because they were already queued to explore.");
        System.out.println("BFS found " + bfs.getSolutionTotal()
                + " solutions, with the best solution having a score of "
                + bfs.getBestScore());
//         bfs.playbackSolution();
    }

}
