package poppitplayer.simplesearch;

import java.util.*;
import simpoppit.gameboard.*;

/**
 * 
 */

/**
 * @author Andrew
 * 
 */
public class SearchNode implements Cloneable, Comparable {

    public SearchNode(GameInterface aState) {
        this.state = aState;
        this.ancestor = null;
        this.operator = null;
        this.depth = 0;
        this.pathCost = 0;
    }

    public SearchNode(GameInterface aState, SearchNode aNode, Coord aCoord,
            int aDepth, int aPathCost) {
        this.state = aState;
        this.ancestor = aNode;
        this.operator = aCoord;
        this.depth = aDepth;
        this.pathCost = aPathCost;
    }

    public GameInterface getState() {
        return this.state;
    }

    public SearchNode getAncestor() {
        return this.ancestor;
    }

    public Coord getOperator() {
        return this.operator;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getPathCost() {
        return this.pathCost;
    }

    public TreeSet<SearchNode> successors() {
        TreeSet<SearchNode> result = new TreeSet<SearchNode>();
        if (!(this.getState().possibleMoves().isEmpty())) {
            for (Coord t : this.getState().possibleMoves()) {
                GameInterface tempState = (GameInterface) this.getState()
                        .clone();
                tempState.pop(t);
                SearchNode tempNode = new SearchNode((GameInterface) tempState
                        .clone(), this, (Coord) t.clone(), this.depth + 1,
                        this.pathCost + 1);
                if (result.contains(tempNode))
                    continue;
                result.add(tempNode);
            }
        }
        return result;
    }

    public boolean equals(Object aSearchNode) {
        if ((aSearchNode instanceof SearchNode)
                && (((SearchNode) aSearchNode).getState().equals(this.state))) {
            return true;
        }
        // && (((SearchNode)aSearchNode).getAncestor().equals(this.ancestor))
        // && (((SearchNode)aSearchNode).getAncestor() == this.ancestor))return
        // true;
        return false;
    }

    public String toString() {
        String result = "";
        result += "State is " + this.state + "\n";
        result += "Ancestor is ";
        if (!(this.ancestor == null))
            result += "not ";
        result += "null.\n";
        result += "Operator is " + this.operator + "\n";
        result += "Depth is " + this.depth + "\n";
        result += "Path cost is " + this.pathCost + "\n";
        return result;
    }

    public Object clone() {
        SearchNode result;
        if (this.operator == null) {
            result = new SearchNode((GameInterface)(this.state.clone()),
                    this.ancestor, (Coord)null, this.depth, this.pathCost);
        } else {
            result = new SearchNode((GameInterface)(this.state.clone()),
                    this.ancestor, (Coord)(this.operator.clone()), this.depth,
                    this.pathCost);
        }
        return result;
    }

    public int compareTo(Object aNode) {
        return this.state.compareTo(((SearchNode) aNode).getState());
    }

    private final GameInterface state;
    private final SearchNode ancestor;
    private final Coord operator;
    private final int depth;
    private final int pathCost;

    /**
     * @param args
     */
    public static void main(String[] args) {
        SearchNode test = new SearchNode(new GameInterface());
        SearchNode testClone = (SearchNode) test.clone();
        if (test.equals(testClone)) {
            System.out.println("Equal");
        } else {
            System.out.println("Not equal");
        }

    }

}
