package EightPuzzlePackage;

import java.util.*;
import java.util.logging.Logger;

/**
 * This class represents the 8-puzzle problem. It contains the board structure which maintains the
 * current state of the puzzle. It also contains the methods to move the blank space in the puzzle
 * , reads a file to initialize the board and prints the board at the current state.
 */
public class EightPuzzle implements Puzzle {

    /* This contains the board that will be configured in the eight puzzle. */
    private char[][] board = {{'b', '1', '2'}, {'3', '4', '5'}, {'6', '7', '8'}};

    /* This contains the row of the blank space. */
    private int blankRow;

    /* This contains the column of the blank space. */
    private int blankCol;

    /* max nodes that should be search in eight puzzle */
    private int maxNodes;

    /* reference to the state, so we can change the state when we need to */
    private State state;

    /* number of moves puzzle has */
    public int numMoves;

    /* logger to for debugging */
    private static final Logger logger = Logger.getLogger(EightPuzzle.class.getName());

    /* This is the constructor for the eight puzzle. */
    public EightPuzzle() {
        this.maxNodes = Integer.MAX_VALUE;
        state = new State(this, null, null);
    }

    char[][] board() {
        return board;
    }

    private char[][] goalState = {{'b', '1', '2'}, {'3', '4', '5'}, {'6', '7', '8'}};

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

    /* This method will specify the max nodes allowed in search */
    public void maxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }

    public char[][] cloneBoard() {
        char[][] clone = new char[board().length][board()[0].length];
        for (int i = 0; i < board().length; i++) {
            for (int j = 0; j < board()[0].length; j++) {
                clone[i][j] = board()[i][j];
            }
        }
        return clone;
    }

    @Override
    /* Make n random moves from the goal state.  */
    public void randomizeState(int n) {
        //scramble the board
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        Random random = new Random();

        //here we should randomize the eight puzzle board
        for (int i = 0; i < n; i++) {
            int randomDirection = random.nextInt(4);
            move(directions[randomDirection]);
        }
    }


    /* This method will move the blank space in the specified direction. */
    public void move(Direction direction) {

        //first we need to find the blank space and set row and col to this.
        findBlankRowandCol();

        int row = blankRow();
        int col = blankCol();

        if (direction == Direction.UP && isValidMove(direction, row, col)) {
            swapUp(row, col);
        } else if (direction == Direction.DOWN && isValidMove(direction, row, col)) {
            swapDown(row, col);
        } else if (direction == Direction.LEFT && isValidMove(direction, row, col)) {
            swapLeft(row, col);
        } else if (direction == Direction.RIGHT && isValidMove(direction, row, col)) {
            swapRight(row, col);
        }
    }

    /* This method will check if move is valid for a possible move.
     * if a move is valid, then it will check for the direction
     * and call for swap method.
     * This will make sure the blank tile will not move off the board
     * */
    boolean isValidMove(Direction direction, int row, int col) {
        //find blank tile first
        findBlankRowandCol();

        //ensuring blank tile doesn't go out of bounds on top.
        if (direction == Direction.UP && row == 0) {
            return false;
        }
        //ensuring blank tile doesn't go out of bounds on bottom.
        if (direction == Direction.DOWN && row == board().length - 1) {
            return false;
        }
        //ensuring blank tile doesn't go out of bounds leftward.
        if (direction == Direction.LEFT && col == 0) {
            return false;
        }
        //ensuring blank tile doesn't go out of bounds rightward
        if (direction == Direction.RIGHT && col == board()[row].length - 1) {
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
        State newState = new State(this, state(), Direction.UP);

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
        State newState = new State(this, state(), Direction.DOWN);

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
        State newState = new State(this, state(), Direction.LEFT);

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
        State newState = new State(this, state(), Direction.RIGHT);

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
        System.out.println(this);
    }

    /* this method will return the string representation of the board */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < board().length; row++) {
            for (int col = 0; col < board()[row].length; col++) {
                sb.append(board()[row][col] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /* this method will calculate the h1 heuristic, num of misplaced tiles */
    public int h1(State state) {
        int misplacedTiles = 0;
        char[][] board = state.board().board();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != goalState[row][col] && board[row][col] != 'b') {
                    misplacedTiles++;
                }
            }
        }

        return misplacedTiles;
    }

    /* this method will calculate the h2 heuristic, manhattan distance */
    public int h2(State state) {
        int h2 = 0;
        char[][] board = state.board().board();

        //for a given char at a row and col, if it doesn't equal goal state then compute manhattan distance
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != goalState[row][col] && board[row][col] != 'b') {
                    h2 += manhattanDistance(board[row][col], row, col);
                }
            }
        }
        return h2;
    }

    /* this method will calculate the manhattan distance of a tile, not including blank tile */
    /* manhattan distance is calculated by |x1 - x2| + |y1 - y2| */
    private int manhattanDistance(char c, int row, int col) {
        return Math.abs(row - goalRow(c)) + Math.abs(col - goalCol(c));
    }

    /* this method will return the row of the goal state of a given tile */
    private int goalRow(char c) {

        //hashmap to store goal row of each tile
        Map<Character, Integer> goalRow = new HashMap<>();
        goalRow.put('1', 0);
        goalRow.put('2', 0);
        goalRow.put('3', 1);
        goalRow.put('4', 1);
        goalRow.put('5', 1);
        goalRow.put('6', 2);
        goalRow.put('7', 2);
        goalRow.put('8', 2);


        return goalRow.get(c);
    }

    /* this method will return the col of goal state of given tile */
    private int goalCol(char c) {
        //hashmap to store goal col of each tile
        Map<Character, Integer> goalCol = new HashMap<>();
        goalCol.put('1', 1);
        goalCol.put('2', 2);
        goalCol.put('3', 0);
        goalCol.put('4', 1);
        goalCol.put('5', 2);
        goalCol.put('6', 0);
        goalCol.put('7', 1);
        goalCol.put('8', 2);

        return goalCol.get(c);
    }

    /* this method will check if the current state is the goal state */
    @Override
    public boolean isGoalState(State state) {

        char[][] board = state.board().board();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != goalState[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void solveAStar(String heuristic) {
        if (heuristic.equals("h1")) {
            solveAStarH1();
        } else if (heuristic.equals("h2")) {
            solveAStarH2();
        } else
            throw new IllegalArgumentException("input not correctly inputted");
    }

    /* A* algo based on H1 heuristic */
    private void solveAStarH1() throws IllegalStateException {
        //path will be a list of states that will be the path to the goal state
        //frontier will be a priority queue that will be sorted by f(n) value
        //visited will be a set of states that have been visited
        List<State> path = new LinkedList<>();
        PriorityQueue<State> frontier = new PriorityQueue<>();
        Map<String, State> visitedSet = new HashMap<>();

        State startingNode = new State(state.board(), null, null);
        startingNode.setDepth(0);
        startingNode.setfWeight(startingNode.depth() + h1(startingNode));
        //add initial state to frontier
        frontier.add(startingNode);


        int counter = 0;
        //while frontier is not empty & counter is less than maxNodes
        while (!frontier.isEmpty() && counter < maxNodes()) {

            //pop the starting state here
            State node = frontier.poll();

            //add the node to visited set or closed list
            visitedSet.put(node.board().toString(), node);

            //check if the node is at the goal state. if it is we break.
            if (isGoalState(node)) break;

            List<State> children = node.neighbors();

            for (State childNode : children) {
                // if childNode is in frontier, and it's depth is less than the lowest depth in frontier,
                // remove it because we have found a better path
                if (frontier.contains(childNode) && childNode.depth() < frontier.peek().depth()) {
                    frontier.remove(childNode);
                }
                //if childNode is in visitedSet, and it's depth is less than it's other path depth in visitedSet,
                // remove it because we have found a better path
                else if (visitedSet.containsValue(childNode) && childNode.depth() < visitedSet.get(childNode.board().toString()).depth()) {
                    visitedSet.remove(childNode.board().toString());
                }
                //if they aren't in frontier or visitedSet, add them to frontier and calculate cost
                else if (!frontier.contains(childNode) && !visitedSet.containsValue(childNode)) {
                    childNode.setfWeight(childNode.depth() + h1(childNode));
                    childNode.setParent(node);
                    frontier.add(childNode);
                }
            }

            counter++;
        }

        //if we reach here then we have not found a solution
        if (counter >= maxNodes) {
            throw new IllegalStateException("No solution found");
        }

        State goalNode = visitedSet.get("b 1 2 \n3 4 5 \n6 7 8 \n");

        //look for path from visitedSet of nodes to see if it's there
        if (goalNode != null) {
            path = getPath(goalNode);
        }

        //print list of path.
        System.out.println(path + "numMoves: " + numMoves + " counter: " + counter);
    }

    /* get path to goal state by backtracking and accessioning parent */
    private List<State> getPath(State goalState) {

        List<Direction> directionsToPath = new ArrayList<>();
        List<State> winningPath = new LinkedList<>();
        Stack<State> path = new Stack<>();
        State currState = goalState;

        //add the goal state to the stack and then access the parent
        while (currState != null) {
            directionsToPath.add(currState.lastDirection());
            path.push(currState);
            currState = currState.parent();
            numMoves++;
        }

        //once we have the path in the stack, we will pop the stack and add it to a list
        while (!path.isEmpty()) {
            winningPath.add(path.pop());
        }

        Collections.reverse(directionsToPath);

        System.out.println(directionsToPath);

        return winningPath;
    }


    /* A* algo based on H2 heuristic */
    private void solveAStarH2() throws IllegalStateException {

        List<State> path = new LinkedList<>();
        PriorityQueue<State> frontier = new PriorityQueue<>();
        Map<String, State> visitedSet = new HashMap<>();

        State startingNode = new State(state.board(), null, null, 0, h2(state));
        startingNode.setfWeight(startingNode.depth() + h2(startingNode));

        frontier.add(startingNode);

        int counter = 0;

        while (!frontier.isEmpty() && counter < maxNodes()) {

            State node = frontier.poll();

            visitedSet.put(node.board().toString(), node);

            if (isGoalState(node)) break;

            List<State> children = node.neighbors();

            for (State child : children) {
                if (frontier.contains(child) && child.depth() < frontier.peek().depth()) {
                    frontier.remove(child);
                } else if (visitedSet.containsValue(child) && child.depth() < visitedSet.get(child.board().toString()).depth()) {
                    visitedSet.remove(child.board().toString());
                } else if (!frontier.contains(child) && !visitedSet.containsValue(child)) {
                    child.setfWeight(child.depth() + h2(child));
                    frontier.add(child);
                    child.setParent(node);
                }
            }

            counter++;
        }

        if (counter >= maxNodes) {
            throw new IllegalStateException("No solution found");
        }

        State goalNode = visitedSet.get("b 1 2 \n3 4 5 \n6 7 8 \n");

        if (goalNode != null) {
            path = getPath(goalNode);
        }

        System.out.println(path + "numMoves: " + numMoves + " counter: " + counter);
    }

    /*
     * This is the eight-puzzle algorithm that will solve local-beam-search.
     */
    @Override
    public void solveBeam(int k) {

        // list to keep track of the k best states
        List<State> kBestStates = new ArrayList<>();


        //start with some starting node
        State startingNode = new State(state.board(), null, null);
        startingNode.setfWeight(h2(startingNode));
        kBestStates.add(startingNode);

        State goalNode = null;
        Map<String, State> visited = new HashMap<>();
        boolean goalFound = false;


        int counter = 0;

        while (counter < maxNodes()) {

            PriorityQueue<State> frontier = new PriorityQueue<>();

            if (goalFound) break;

            //for each state in kBestStates, we add its children to the frontier
            for (State kthNode : kBestStates) {
                //add all the k states to the set, avoids infinite loops
                visited.put(kthNode.board().toString(), kthNode);
                //expand kth successors and gather cost info for every node, then put into frontier
                for (State childNode : kthNode.neighbors()) {
                    if (!visited.containsValue(childNode)) {
                        childNode.setfWeight(h2(childNode));
                        childNode.setParent(kthNode);
                        frontier.add(childNode);
                    }
                }
            }

            //clear the kBestStates list
            kBestStates.clear();

            // add the childNodes from the frontier to the kBestStates and check if they are goal states
            for (int i = 0; i < k && !frontier.isEmpty(); i++) {
                kBestStates.add(frontier.poll());
                if (isGoalState(kBestStates.get(i))) {
                    goalNode = kBestStates.get(i);
                    goalFound = true;
                }
            }

            //increment counter to get to the max num of nodes
            counter++;
        }

        if (counter >= maxNodes()) {
            System.out.println("No solution found");
        }

        //get the path to the goal state, if it exists, if not throw an exception
        List<State> goalPath = getPath(goalNode);

        System.out.println(goalPath + "numMoves: " + numMoves + " counter: " + counter);
    }
}

