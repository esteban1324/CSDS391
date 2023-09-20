package EightPuzzlePackage;

/* interface for eight-puzzle and 2by2 rubix cube, these puzzles share commonalities */
public interface Puzzle {

    void setState(State state);

    void printState();

    void randomizeState(int nMoves);

    boolean isGoalState(State state);


    void solveAStar(String heuristic);

    void solveBeam(int maxNodes);

    void maxNodes(int maxNodes);

}
