import java.util.Stack;

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
        System.out.println("Queuing " + this.node.successors().size() + " nodes.");
        int beforeSize = this.UnseenSize();
        for (SearchNode t : this.node.successors()){
            if (this.SeenContains(t)) continue;
            if (this.UnseenContains(t)) continue;
            this.EnqueueUnseen(t);
        }
        System.out.println("(Actually queued " + (this.UnseenSize() - beforeSize) + " nodes)");
    }
    
    public void search(){
        while (!(this.UnseenEmpty())){
        //while((!(this.solutionFound))&(!(this.UnseenEmpty()))){
            System.out.println("Tested " + this.SeenSize() + " nodes so far.");
            System.out.println("Found " + this.solutionsFound + " solutions so far.");
            System.out.println(this.UnseenSize() + " nodes remain in the current queue.");
            System.out.print("Testing node...");
            if (this.goalState()){
                System.out.println("found a solution!");
                System.out.println("Score of solution found is " + this.node.getState().getScore());
                this.solutionFound = true;
                this.solutionsFound++;
                if (this.node.getState().getScore() > this.bestScore){
                    this.bestScore = this.node.getState().getScore();
                    this.bestNode = this.node;
                    System.out.println("Setting score to " + this.bestScore);
                    System.out.println("Setting best node to " + this.bestNode);
                }
                //break;
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
            //gui.setVisible(false);
            //gui.dispose();
            gui = null;
            this.playbackSolution();
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
    
    abstract public boolean UnseenContains(SearchNode aNode);
    
    protected SearchNode node;
    protected SearchNode bestNode;
    protected int bestScore;
    protected int solutionsFound;
    protected boolean solutionFound;

}
