import java.util.*;
/**
 * 
 */

/**
 * @author Andrew
 *
 */
public class SearchNode implements Cloneable{

    public SearchNode(GameInterface aState){
        this.state = aState;
        this.ancestor = null;
        this.operator = null;
        this.depth = 0;
        this.pathCost = 0;
    }
    
    public SearchNode(GameInterface aState, SearchNode aNode, Coord aCoord, int aDepth, int aPathCost){
        this.state = aState;
        this.ancestor = aNode;
        this.operator = aCoord;
        this.depth = aDepth;
        this.pathCost = aPathCost;
    }
    
    public GameInterface getState(){
        return this.state;
    }
    
    public SearchNode getAncestor(){
        return this.ancestor;
    }
    
    private void setAncestor(SearchNode aNode){
        this.ancestor = aNode;
    }
    
    public Coord getOperator(){
        return this.operator;
    }
    
    public int getDepth(){
        return this.depth;
    }
    
    private void setDepth(int aDepth){
        this.depth = aDepth;
    }
    
    public int getPathCost(){
        return this.pathCost;
    }
    
    public ArrayList<SearchNode> successors(){
        ArrayList<SearchNode> result = new ArrayList<SearchNode>();
        if (!(this.getState().possibleMoves().isEmpty())){
            for (Coord t : this.getState().possibleMoves()){
                GameInterface tempState = (GameInterface)this.getState().clone();
                tempState.pop(t);
                result.add(new SearchNode((GameInterface)tempState.clone(), this, (Coord)t.clone(), this.depth+1, this.pathCost+1));
            }
        }
        return result;
    }
    
    public boolean equals(Object aSearchNode){
        if ((aSearchNode instanceof SearchNode)
                && (((SearchNode)aSearchNode).getState().equals(this.state)))return true;
                //&& (((SearchNode)aSearchNode).getAncestor().equals(this.ancestor))
                //&& (((SearchNode)aSearchNode).getAncestor() == this.ancestor))return true;
        return false;
    }
    
    public String toString(){
        String result = "";
        result += "State is " + this.state.getGrid() + "\n";
        result += "Ancestor is ";
        if (!(this.ancestor == null)) result += "not ";
        result += "null.\n";
        result += "Operator is " + this.operator + "\n";
        result += "Depth is " + this.depth + "\n";
        result += "Path cost is " + this.pathCost + "\n";
        return result;
    }
    
    public Object clone(){
        SearchNode result;
        if (this.operator == null){
            result = new SearchNode((GameInterface)(this.state.clone()), this.ancestor, (Coord)null, this.depth, this.pathCost);
        }else{
            result = new SearchNode((GameInterface)(this.state.clone()), this.ancestor, (Coord)(this.operator.clone()), this.depth, this.pathCost);
        }
        return (Object)result;
    }
    
    private GameInterface state;
    private SearchNode ancestor;
    private Coord operator;
    private int depth;
    private int pathCost;
    /**
     * @param args
     */
    public static void main(String[] args) {
        SearchNode test = new SearchNode(new GameInterface());
        SearchNode testClone = (SearchNode)test.clone();

    }

}
