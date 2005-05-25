import java.util.*;

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

    public SearchNode getNode(){
        return this.node;
    }
    
    public boolean goalState(){
        if (this.node == null) return false;
        return this.node.getState().gameOver();
    }
    
    public int nodeScore (){
        return this.node.getState().getScore();
    }
    
    public void nextNode(){
        if (this.unseenEmpty()) this.node = null;
        else {
            this.node = this.dequeueUnseen();
            this.unseenDrop(this.node);
        }
    }
    
    public void expand(){
        //System.out.println("Queuing " + this.node.successors().size() + " nodes.");
        //int beforeSize = this.unseenSize();
        for (SearchNode t : this.node.successors()){
            if (this.seenContains(t)) continue;
            if (this.unseenContains(t)) continue;
            this.enqueueUnseen(t);
            this.storeUnseen(t);
            if (this.unseenNodes.size() != this.queueSize()){
                System.out.println("Uh-oh");
                System.exit(0);
            }
        }
        //this.totalNodes += (this.unseenSize() - beforeSize);
        //System.out.println("(Actually queued " + (this.unseenSize() - beforeSize) + " nodes)");
        //System.out.println("Generated " + this.totalNodes + " total nodes so far.");
    }
    
    public void search(){
        int loopCount = 0;
        while (!(this.unseenEmpty())){
            loopCount++;
            System.out.print(".");
            if (loopCount%40 == 0) System.out.println("(" + loopCount + " nodes)");
            if (this.goalState()){
                System.out.println("\nFound solution after examining " + loopCount + " nodes.");
                //System.out.println("Found a solution!");
                System.out.println("Score of solution found is " + this.node.getState().getScore());
                System.out.println("Max score possible for this game is " + this.node.getState().getMaxScore());
                System.out.println("Tested " + this.seenSize() + " nodes so far.");
                System.out.println("Found " + this.solutionsFound + " solutions so far.");
                System.out.println(this.unseenSize() + " nodes remain in the current queue.");
                this.solutionsFound++;
                if ((this.node.getState().getScore() > this.bestScore)){
                    this.bestScore = this.node.getState().getScore();
                    this.bestNode = this.node;
                    System.out.println("Setting score to " + this.bestScore);
                    System.out.println("Setting best node to " + this.bestNode);
                    if ((this.bestNode.getState().getScore() == this.bestNode.getState().getMaxScore())){
                        System.out.println("Found perfect game, abandoning search.");
                        this.perfect = true;
                        break;
                    }
                }
                loopCount = 0;
                //break;
            }
            this.storeSeen(this.node);
            if (!(this.goalState())){
                this.expand();
            }
            this.nextNode();
        }
        if (!this.perfect){
        System.out.println("\nFound solution after examining " + loopCount + " nodes.");
        System.out.println("Score of solution found is " + this.bestNode.getState().getScore());
        System.out.println("Max score possible for this game is " + this.bestNode.getState().getMaxScore());
        System.out.println("Tested " + this.seenSize() + " nodes so far.");
        System.out.println("Found " + this.solutionsFound + " solutions so far.");
        System.out.println(this.unseenSize() + " nodes remain in the current queue.");
        System.out.println("Exhausted search space, no perfect game found for this board.");
        }
    }
    
    public void playbackSolution(){
        s_runtime.runFinalization();
        s_runtime.gc();
        Thread.yield();
        SearchNode myNode = this.bestNode;
        //SearchNode myNode = this.node;
        Stack<Coord> moves = new Stack<Coord>();
        //GameInterface myGame = gui.getGame();
        while(myNode.getAncestor() != null){
            moves.push(myNode.getOperator());
            myNode = myNode.getAncestor();
        }
        SimPoppitGui gui = new SimPoppitGui((GameInterface)myNode.getState().clone(), false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        System.out.println("Number of moves is " + moves.size());
        JOptionPane.showMessageDialog(gui, "Click OK to watch playback", "Game Solved", JOptionPane.DEFAULT_OPTION);
        while(!(moves.isEmpty())){
            gui.getGame().highlight(moves.peek());
            try{
                Thread.sleep(2000);
            }
            catch (Exception e){
                System.out.println("Failed to sleep - " + e);
            }
            gui.getGame().pop(moves.pop());
        }
        int selection = JOptionPane.showConfirmDialog(gui, "Click OK to watch playback again or cancel not to", "Game Solved", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.CANCEL_OPTION){
            //System.exit(0);
        }else{
            gui.setVisible(false);
            gui.dispose();
            gui = null;
            this.playbackSolution();
        }
    }
    
    public void storeSeen(SearchNode aNode){
        this.seenNodes.add(aNode.getState().getGrid().getGridAsBalloonInt());
    }
    
    public boolean seenEmpty(){
        return this.seenNodes.isEmpty();
    }
    
    public int seenSize(){
        return this.seenNodes.size();
    }
    
    public boolean seenContains(SearchNode aNode){
        return this.seenNodes.contains(aNode.getState().getGrid().getGridAsBalloonInt());
    }
    
    public void storeUnseen(SearchNode aNode){
        this.unseenNodes.add(aNode.getState().getGrid().getGridAsBalloonInt());
    }
    
    public boolean unseenEmpty(){
        return this.unseenNodes.isEmpty();
    }
    
    public int unseenSize(){
        return this.unseenNodes.size();
    }
    
    public boolean unseenContains(SearchNode aNode){
        return this.unseenNodes.contains(aNode.getState().getGrid().getGridAsBalloonInt());
    }
    
    public void unseenDrop(SearchNode aNode){
        this.unseenNodes.remove(aNode.getState().getGrid().getGridAsBalloonInt());
    }
    
    abstract public void enqueueUnseen(SearchNode aNode);
    
    abstract public SearchNode dequeueUnseen();
    
    abstract public int queueSize();
    
    
    protected SearchNode node;
    protected SearchNode bestNode;
    protected int bestScore;
    protected int solutionsFound;
    //protected int totalNodes;
    protected boolean perfect;
    protected TreeSet<BalloonInt> seenNodes;
    protected TreeSet<BalloonInt> unseenNodes;
    private static final Runtime s_runtime = Runtime.getRuntime ();
    
    public static void main(String[] args){
        GameInterface game = new GameInterface(7,7);
        BreadthFirstSearch bfs = new BreadthFirstSearch(game);
        DepthFirstSearch dfs = new DepthFirstSearch(game);
        bfs.search();
        dfs.search();
        bfs.playbackSolution();
        dfs.playbackSolution();
    }

}
