import java.util.*;

import javax.swing.JFrame;
/**
 * 
 */

/**
 * @author Andrew
 *
 */
public class Searchtemp {

    public Searchtemp(GameInterface initialState){
        this.node = new SearchNode(initialState);
        this.unseenStates = new Stack<SearchNode>();
        this.seenStates = new Stack<SearchNode>();
        this.bestNode = null;
        this.bestScore = 0;
        this.solutionFound = false;
        this.unseenStates.push(this.node);
    }
    
    public SearchNode getNode(){
        return this.node;
    }
    
    public boolean goalState(){
        if (this.node == null) return false;
        return this.node.getState().gameOver();
    }
    
    public boolean solutionFound(){
        return this.solutionFound;
    }
    
    public int nodeScore (){
        return this.node.getState().getScore();
    }
    
    public void nextNode(){
        if (this.unseenStates.isEmpty()) this.node = null;
        else this.node = this.unseenStates.pop();
    }
    
    public void expand(){
        System.out.println("Pushing " + this.node.successors().size() + " nodes onto stack.");
        for (SearchNode t : this.node.successors()){
            if (this.seenStates.contains(t)) continue;
            this.unseenStates.push(t);
        }
    }
    
    public void search(){
        while (!(this.unseenStates.isEmpty())){
            System.out.println("Tested " + this.seenStates.size() + " nodes so far!");
            System.out.println(this.unseenStates.size() + " nodes remain in the current queue.");
            System.out.print("Testing node...");
            if (this.goalState()){
                System.out.println("found a solution!");
                System.out.println("Score of solution found is " + this.node.getState().getScore());
                if (this.node.getState().getScore() > this.bestScore){
                    this.bestScore = this.node.getState().getScore();
                    this.bestNode = this.node;
                    System.out.println("Setting score to " + this.bestScore);
                    System.out.println("Setting best node to " + this.bestNode);
                }
                break;
            }else{
                System.out.println("not a solution.");
            }
            this.seenStates.push(this.node);
            this.expand();
            this.nextNode();
        }
        if (!(this.bestNode == null)) this.solutionFound = true;
    }
    
    public void playbackSolution(){
        SearchNode myNode = this.bestNode;
        Stack<Coord> moves = new Stack<Coord>();
        //GameInterface myGame = gui.getGame();
        while(myNode.getAncestor() != null){
            moves.push(myNode.getOperator());
            myNode = myNode.getAncestor();
        }
        SimPoppitGui gui = new SimPoppitGui(myNode.getState());
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        System.out.println("Number of moves is " + moves.size());
        while(!(moves.isEmpty())){
            gui.getGame().pop(moves.pop());
            try{
                Thread.sleep(2000);
            }
            catch (Exception e){
                System.out.println("Failed to sleep - " + e);
            }
        }
    }
    
    private SearchNode node;
    private Stack<SearchNode> unseenStates;
    private Stack<SearchNode> seenStates;
    //private Queue<SearchNode> unseenStates;
    //private Queue<SearchNode> seenStates;
    private SearchNode bestNode;
    private int bestScore;
    private boolean solutionFound;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Searchtemp test = new Searchtemp(new GameInterface());
        test.search();
        if (test.solutionFound()){
            System.out.println("Solution found in " + test.getNode().getDepth() + " moves.");
            test.playbackSolution();
        }else{
            System.out.println("No solution found.");
        }
        
    }

}
