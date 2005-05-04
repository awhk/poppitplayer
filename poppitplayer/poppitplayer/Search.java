import java.util.Stack;

import javax.swing.JFrame;

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
    
    public boolean solutionFound(){
        return this.solutionFound;
    }
    
    public int nodeScore (){
        return this.node.getState().getScore();
    }
    
    public void nextNode(){
        if (this.UnseenEmpty()) this.node = null;
        else this.node = this.DequeueUnseen();
    }
    
    public void expand(){
        System.out.println("Pushing " + this.node.successors().size() + " nodes onto stack.");
        for (SearchNode t : this.node.successors()){
            if (this.SeenContains(t)) continue;
            this.EnqueueUnseen(t);
        }
    }
    
    public void search(){
        while (!(this.UnseenEmpty())){
            System.out.println("Tested " + this.SeenSize() + " nodes so far!");
            System.out.println(this.UnseenSize() + " nodes remain in the current queue.");
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
            this.EnqueueSeen(this.node);
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
            try{
                Thread.sleep(2000);
            }
            catch (Exception e){
                System.out.println("Failed to sleep - " + e);
            }
            gui.getGame().pop(moves.pop());
        }
    }
    
    abstract public void EnqueueUnseen(SearchNode aNode);
    
    abstract public void EnqueueSeen(SearchNode aNode);
    
    abstract public SearchNode DequeueUnseen();
    
    abstract public SearchNode DequeueSeen();
    
    abstract public boolean SeenEmpty();
    
    abstract public boolean UnseenEmpty();
    
    abstract public int UnseenSize();
    
    abstract public int SeenSize();
    
    abstract public boolean SeenContains(SearchNode aNode);
    
    protected SearchNode node;
    protected SearchNode bestNode;
    protected int bestScore;
    protected boolean solutionFound;

}
