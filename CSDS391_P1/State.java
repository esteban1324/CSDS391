package EightPuzzlePackage;

import java.util.*;
/*
*  This class represents the state of the puzzle. It contains the reference to the board structure which maintains the puzzle itself
* and contains a reference to the parent state, whose board is the state of the puzzle before the current state.
*
*/
public final class State {

    /* contains reference to board at given state */
    private EightPuzzle board;

    /* contains reference to parent state */
    private State parent;

    /* contains the direction of last move */
    private Direction direction;

    /* contains the depth of the state */
    private int depth;

    /* contains the cost of the state */
    private int cost;


    public State(EightPuzzle board, State parent, int depth, int cost) {
        this.board = board;
        this.parent = parent;
        this.depth = depth;
        this.cost = cost;
    }


    public EightPuzzle board() {
        return board;
    }

    public State parent() {
        return parent;
    }

    public int depth() {
        return depth;
    }

    public int cost() {
        return cost;
    }

    /* method that print current puzzle state */
    public void printState() {
        board().printState();
    }


}
