package EightPuzzlePackage;

import java.util.*;
/*
*  This class represents the state of the puzzle. It contains the reference to the board structure which maintains the puzzle itself
* and contains a reference to the parent state, whose board is the state of the puzzle before the current state.
* This implements a comparable interface as the states function weights will be compared to each other in the algorithms
*/
public final class State implements Comparable<State> {

    /* contains reference to board at given state */
    private final EightPuzzle board;

    /* contains reference to parent state */
    private State parent;

    /* contains the direction of last move */
    private final Direction lastDirection;

    /* total cost of the start node to curr node calculated by f(n) = g(n) + h(n) */
    private int fWeight;

    /* contains the depth of this state */
    private int depth;

    /* constructor for initial state */
    public State(EightPuzzle board, State parent, Direction lastDirection){
        this.board = board;
        this.parent = parent;
        this.lastDirection = lastDirection;
        this.depth = 0;
        this.fWeight = 0;
    }

    /* constructor for neighbors */
    public State(EightPuzzle board, State parent, Direction lastDirection, int depth){
        this.board = board;
        this.parent = parent;
        this.lastDirection = lastDirection;
        this.depth = depth;
        this.fWeight = 0;
    }

    /* constructor for weight */
    public State(EightPuzzle board, State parent, Direction lastDirection, int depth, int fWeight){
        this.board = board;
        this.parent = parent;
        this.lastDirection = lastDirection;
        this.depth = depth;
        this.fWeight = fWeight;
    }

    /* method that finds blank space in the state */
    public void findBlankRowandCol(){
        //traverse through the board
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                //if blank space is found, return the row and column
                if (board().board()[i][j] == 'b'){
                    board().blankRow(i);
                    board().blankCol(j);
                }
            }
        }
    }


    public EightPuzzle board() {
        return board;
    }

    public State parent() {
        return parent;
    }

    /* method that sets parent state to a given state */
    public void setParent(State parent) {
        this.parent = parent;
    }


    /* method that returns the depth of the state */
    public int depth() {
        return depth;
    }

    /* method that sets the depth of the state */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int fWeight() {
        return fWeight;
    }

    public void setfWeight(int fWeight) {
        this.fWeight = fWeight;
    }

    /* method that returns the direction of the last move */
    public Direction lastDirection() {
        return lastDirection;
    }


    /* method that is toString representation of the state */
    public String toString() {
        return board().toString();
    }


    /* method that gets neighbors of current state */
    public List<State> neighbors(){

        List<State> neighbors = new ArrayList<>();
        State initialState = clone();
        EightPuzzle initialBoard = initialState.board();
        findBlankRowandCol();

        // traverse through all directions {Up, Down, Left, Right}
        for (Direction dir: Direction.values()){

            if(initialBoard.isValidMove(dir, board().blankRow(), board().blankCol())){
                //create new board and state to avoid modifying the current state
                EightPuzzle newBoard = new EightPuzzle();
                newBoard.setBoard(board().cloneBoard());

                //move the blank space in the new board
                newBoard.move(dir);

                //create new state with depth increasing by for g(n) calculation.
                State newState = new State(newBoard, initialState, dir, initialState.depth() + 1);

                neighbors.add(newState);
            }

        }
        return neighbors;
    }

    /* method that clones the current state, avoid modifying actual board */
    public State clone(){
        return new State(this.board(), parent, lastDirection, depth);
    }

    /* we will compare the weights in the pq. pq will be sorted by min cost */
    @Override
    public int compareTo(State o) {
        return this.fWeight() - o.fWeight();
    }

}
