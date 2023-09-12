package EightPuzzlePackage;


/**
 * This class represents the 8-puzzle problem. It contains the board structure which maintains the
 * current state of the puzzle. It also contains the methods to move the blank space in the puzzle
 * , reads a file to initialize the board and prints the board at the current state.
 */
public class EightPuzzle implements Puzzle{

    /* This contains the board that will be configured in the eight puzzle. */
    private char[][] board;

    /* This contains the row of the blank space. */
    private int blankRow;
    /* This contains the column of the blank space. */
    private int blankCol;

    /* max nodes that should be search in eight puzzle */
    private int maxNodes;

    /* reference to the state, so we can change the state when we need to */
    private State state;

    /* This is the constructor for the eight puzzle. */
    public EightPuzzle() {
        board = new char[][]{{'b', '1', '2'}, {'3', '4', '5'}, {'6', '7', '8'}};
        this.maxNodes = Integer.MAX_VALUE;
        state = new State(this, null, 0, 0);
    }

    /* This method will read the file and initialize the board. */
    public void initializeBoard(String fileName) {

        //here we should randomize the eight puzzle board

        //we should then read the commands the user inputs and move the blank space accordingly

        //we should then print the board after each move

        //we should then check if the board is in the goal state


    }

    @Override
    /* Make n random moves from the goal state.  */
    public void randomizeState(int n) {

        //here we should randomize the eight puzzle board
        for (int i = 0; i < n; i++) {


        }
    }

    /* This method will specify the max nodes allowed in search */
    public void maxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }

    /* This method will move the blank space in the specified direction. */
    public void move(Direction direction) {
        int row = blankRow();
        int col = blankCol();

        //add case for when tile is on the edge of the board and cannot be moved in that direction. Throw exception
        if (direction == Direction.UP && board()[row][col] == 'b') {
            swapUp(row, col);
        }
        else if (direction == Direction.DOWN && board()[row][col] == 'b') {
            swapDown(row, col);
        } else if (direction == Direction.LEFT && board()[row][col] == 'b') {
            swapLeft(row, col);
        } else if (direction == Direction.RIGHT && board()[row][col] == 'b') {
            swapRight(row, col);
        }
    }

    /* This method will check if move is valid for a possible move.
     * if a move is valid, then it will check for the direction
     * and call for swap method.
     * This will make sure the blank tile will not move off the board
     * */
    private boolean isValidMove(Direction direction, int row, int col){

        //ensuring blank tile doesn't go out of bounds on top.
        if(direction == Direction.UP && row == 0){
            return false;
        }
        //ensuring blank tile doesn't go out of bounds on bottom.
        if(direction == Direction.DOWN && row == board().length - 1){
            return false;
        }
        //ensuring blank tile doesn't go out of bounds leftward.
        if(direction == Direction.LEFT && col == 0){
            return false;
        }
        //ensuring blank tile doesn't go out of bounds rightward
        if(direction == Direction.RIGHT && col == board()[row].length - 1){
            return false;
        }





        return true;
    }

    /* helper method to perform swap of blank tile up a tile */
    private void swapUp(int row, int col) {

        //save number above blank tile to temp variable
        char temp = 'b';

        //swap blank tile with number above it
        board()[row][col] = board()[row - 1][col];

        //swap number above blank tile with blank tile
        board()[row - 1][col] = temp;

        //save new state with new board
        State newState = new State(this, state(), state.depth() + 1, state.cost() + 1);

        setState(newState);

        //set blank row and col
        blankRow(row - 1);
        blankCol(col);
    }

    /* helper method to perform swap of blank tile up a tile, we then save it to a new state which */
    private void swapDown(int row, int col) {
        //save number below blank tile to temp variable
        char temp = 'b';

        //swap blank tile with number below it
        board()[row][col] = board()[row + 1][col];

        //swap number below blank tile with blank tile
        board()[row + 1][col] = temp;

        //save new state with new board
        State newState = new State(this, state(), state.depth() + 1, state.cost() + 1);

        setState(newState);

        //set blank row and col
        blankRow(row + 1);
        blankCol(col);
    }

    /* helper method to perform swap of blank tile left a tile */
    private void swapLeft(int row, int col) {
        //save number to the left of blank tile to temp variable
        char temp = 'b';

        //swap blank tile with number to the left of it
        board()[row][col] = board()[row][col - 1];

        //swap number to the left of blank tile with blank tile
        board()[row][col - 1] = temp;

        //save new state with new board
        State newState = new State(this, state(), state.depth() + 1, state.cost() + 1);

        setState(newState);

        //set blank row and col
        blankRow(row);
        blankCol(col - 1);
    }

    /* helper method to perform swap of blank tile right a tile */
    private void swapRight(int row, int col) {
        //save number to the right of blank tile to temp variable
        char temp = 'b';

        //swap blank tile with number to the right of it
        board()[row][col] = board()[row][col + 1];

        //swap number to the right of blank tile with blank tile
        board()[row][col + 1] = temp;

        //save new state with new board
        State newState = new State(this, state(), state.depth() + 1, state.cost() + 1);

        setState(newState);

        //set blank row and col
        blankRow(row);
        blankCol(col + 1);
    }

    /* this method will find and save blank row, col */
    private void findBlankRowandCol() {
        for (int row = 0; row < board().length; row++) {
            for (int col = 0; col < board()[row].length; col++) {
                if (board()[row][col] == 'b') {
                    blankRow(row);
                    blankCol(col);
                }
            }
        }
    }

    /* printing board of current state */
    public void printState() {
        for (int row = 0; row < board().length; row++) {
            for (int col = 0; col < board()[row].length; col++) {
                System.out.print(board()[row][col] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public boolean isGoalState(State state) {
        return false;
    }

    @Override
    public boolean isSolveable(State state) {
        return false;
    }

    @Override
    public void solveAStar(String heuristic){

    }

    @Override
    public void solveBeam(int k){

    }

    @Override
    public void solveDFS(){

    }

    @Override
    public void solveBFS(){

    }



     char[][] board() {
        return board;
    }

     void setBoard(char[][] board) {
        this.board = board;
    }

     int blankRow() {
        return blankRow;
    }

     void blankRow(int blankRow) {
        this.blankRow = blankRow;
    }

     int blankCol() {
        return blankCol;
    }

     void blankCol(int blankCol) {
        this.blankCol = blankCol;
    }

    public int maxNodes() {
        return maxNodes;
    }

    public void setState(State state) {
        this.state = state;
    }

    State state() {
        return state;
    }

}
