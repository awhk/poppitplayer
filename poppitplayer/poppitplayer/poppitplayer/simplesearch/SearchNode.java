package poppitplayer.simplesearch;

import java.util.*;
import simpoppit.gameboard.*;

/**
 * <p>
 * Title: SearchNode
 * </p>
 * 
 * <p>
 * Description: The SearchNode class defines a node in a search tree as utilized
 * by the {@link Search} class. This class encapsulates a specific game state,
 * including a pointer to the parent state (which may have a pointer to its
 * parent state, and so on defining a particular progression through the game
 * state). Other information codified by this class is the depth of the given
 * state (i.e., how many moves were made to arrive at the given state), and the
 * last move made that resulted in the current state. One typical piece of
 * information that typically would be included that is absent here is a path
 * cost, or some similar measure of how "expensive" a given solution is. Poppit
 * does not take path cost into account. Only the end result is significant.
 * </p>
 * 
 * <p>
 * Note there is no default constructor for this class (a reference to an
 * exisiting {@link GameInterface} must be passed).
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Andrew W. Henry
 * @version 1.0
 * @see Search
 * @see GameInterface
 * 
 */
public class SearchNode implements Cloneable, Comparable {

    /**
     * Common constructor to initialize the first node in the search space.
     * Takes a (typically) unplayed game in the form of a {@link GameInterface}
     * object.
     * 
     * @param aState
     *            an existing (though typically unplayed) game
     */
    public SearchNode(GameInterface aState) {
        // Initialize our internal game state
        this.state = aState;
        // At the outset, there is no ancestor node
        this.ancestor = null;
        // And no last move
        this.operator = null;
        // And the depth is zero
        this.depth = 0;
    }

    /**
     * This constructor is used by the clone method to propogate the accumulated
     * depth, as well as create a pointer to a previous search node.
     * 
     * @param aState
     *            a new game state created as a result of a move perfromed on
     *            the ancestor state
     * @param aNode
     *            the node previous to this one, having a depth one less and a
     *            prior game state
     * @param aCoord
     *            the move that was made to arrive at this state
     */
    public SearchNode(GameInterface aState, SearchNode aNode, Coord aCoord) {
        // Initialize internal game state
        this.state = aState;
        // Keep reference to previous node/state
        this.ancestor = aNode;
        // Note the move that got us here
        this.operator = aCoord;
        // Keep track of the new depth (number of moves)
        this.depth = aNode.depth + 1;
    }

    /**
     * Return this node's state.
     * 
     * @return current state
     * @see GameInterface
     */
    public GameInterface getState() {
        return this.state;
    }

    /**
     * Return this node's ancestor state.
     * 
     * @return previous state
     */
    public SearchNode getAncestor() {
        return this.ancestor;
    }

    /**
     * Return this node's last move
     * 
     * @return {@link Coord}inate of last move
     */
    public Coord getOperator() {
        return this.operator;
    }

    /**
     * Return this node's depth (number of moves to get to current state).
     * 
     * @return current integer depth value
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Return a set of successor search nodes generated from the current state.
     * That is, given the current state, generate all possible moves for that
     * state, remove duplicate states generated, and create new SearchNodes for
     * the resulting game states setting this node as the parent.
     * 
     * @return set of unique child search nodes based on the current game state
     */
    public TreeSet<SearchNode> successors() {
        // Initialize storage for return value
        TreeSet<SearchNode> result = new TreeSet<SearchNode>();

        // If this state is a terminal state (i.e. the current node represents a
        // "game over" condition), return an empty set of nodes;
        if (this.state.isGameOver())
            return result;
        // Otherwise, get a list of possible moves and iterate through them
        for (Coord t : this.state.getPossibleMoves()) {
            // For each possible move, make a copy of the current game state
            GameInterface tempState = (GameInterface) this.state.clone();
            // Make the move on the copy
            tempState.pop(t);
            // Make a new SearchNode using the resulting game state, this
            // (parent) node, and a copy of the move that was made.
            SearchNode tempNode = new SearchNode((GameInterface) tempState
                    .clone(), this, (Coord) t.clone());
            // And add this new successor node to the result set. The
            // uniqueness contract of the set means it will automatically
            // throw away duplicate nodes.
            result.add(tempNode);
        }
        // Return the set of successor nodes
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object aSearchNode) {
        // If the passed object is a SearchNode, and the game states of that
        // node and this node are equivalent, then the nodes are equivalent.
        // Even though they may have different depths and parent nodes, if they
        // represent the same board layout, how they got to that layout is of no
        // significance.
        if ((aSearchNode instanceof SearchNode)
                && (((SearchNode) aSearchNode).state.equals(this.state))) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // Initialize a result string
        String result = "";
        // Append the current GameInterface
        result += "State is " + this.state + "\n";
        // Note whether or not this node has a parent node (enumerating the
        // parent node recursively would generate WAY too much output)
        result += "Ancestor is ";
        if (!(this.ancestor == null))
            result += "not ";
        result += "null.\n";
        // Append the last move (grid location)
        result += "Operator is " + this.operator + "\n";
        // Append the depth of the current node
        result += "Depth is " + this.depth + "\n";
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        // Prepare a result variable
        SearchNode result;
        // If the last move does not exist, take special action (pass a null to
        // the clone constructor). This is needed so we don't call "clone" on a
        // null object (which I assume would not work).
        if (this.operator == null) {
            result = new SearchNode((GameInterface) (this.state.clone()),
                    this.ancestor, (Coord) null);
        } else {
            result = new SearchNode((GameInterface) (this.state.clone()),
                    this.ancestor, (Coord) (this.operator.clone()));
        }
        return result;
    }

    /**
     * @see java.lang.Comparable
     */
    public int compareTo(Object aNode) {
        return this.state.compareTo(((SearchNode) aNode).state);
    }

    private final GameInterface state;

    private final SearchNode ancestor;

    private final Coord operator;

    private final int depth;

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
