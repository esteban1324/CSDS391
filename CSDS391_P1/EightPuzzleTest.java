package EightPuzzlePackage;

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EightPuzzleTest {

    @Test
    public void moveFunctionality() {


        // Create a new board
        EightPuzzle board = new EightPuzzle();

        //test eligible moves
        State state = board.state();

        // assert toString board is correct
        assertEquals("b 1 2 \n3 4 5 \n6 7 8 \n", state.board().toString());

        // Test 1: Move DOWN
        board.move(Direction.DOWN);

        assertEquals("3 1 2 \nb 4 5 \n6 7 8 \n", state.board().toString());


        // Test 2: Move RIGHT
        board.move(Direction.RIGHT);

        assertEquals("3 1 2 \n4 b 5 \n6 7 8 \n", state.board().toString());

        // Test 3: Move UP
        board.move(Direction.UP);

        assertEquals("3 b 2 \n4 1 5 \n6 7 8 \n", state.board().toString());

        // Test 4: Move LEFT
        board.move(Direction.LEFT);

        assertEquals("b 3 2 \n4 1 5 \n6 7 8 \n", state.board().toString());

        /* board cases work for valid moves */

        //test invalid moves, 'b' should not move

        //Test 5: Move Left
        board.move(Direction.LEFT);

        assertEquals("b 3 2 \n4 1 5 \n6 7 8 \n", state.board().toString());

        //Test 6: Move Up
        board.move(Direction.UP);

        assertEquals("b 3 2 \n4 1 5 \n6 7 8 \n", state.board().toString());

        //Test 7: Move tile to bottom right corner
        EightPuzzle board2 = new EightPuzzle();

        board2.move(Direction.DOWN);
        board2.move(Direction.DOWN);
        board2.move(Direction.RIGHT);
        board2.move(Direction.RIGHT);

        assertEquals("3 1 2 \n6 4 5 \n7 8 b \n", board2.toString());

    }

    @Test
    void setState() {
        //verify that we can set board to any random state,
        // and it can move from blank random state

        //Set up a new board and state
        EightPuzzle board = new EightPuzzle();

        //Set the state to a random state
        char[][] randomBoard = new char[][]{{'5', '6', '3'}, {'2', '1', '4'}, {'b', '8', '7'}};
        board.setBoard(randomBoard);

        //assert that the board is in the correct state
        assertEquals("5 6 3 \n2 1 4 \nb 8 7 \n", board.toString());

        //move blank space to middle tile
        board.move(Direction.UP);

        //assert that the board is in the correct state
        assertEquals("5 6 3 \nb 1 4 \n2 8 7 \n", board.toString());

        board.move(Direction.RIGHT);

        //assert that the board is in the correct state
        assertEquals("5 6 3 \n1 b 4 \n2 8 7 \n", board.toString());
    }

    @Test
    void testNeighborStates() {

        //Set up a new board and state
        //goal board is the following:
        /* b 1 2
         * 3 4 5
         * 6 7 8
         */
        EightPuzzle board = new EightPuzzle();

        State state = board.state();

        //get adjacent states
        List<State> neighbors = state.neighbors();


        board.move(Direction.DOWN);

        //get adjacent states
        neighbors = state.neighbors();
    }

    @Test
    public void testDepth() {
        //set up a new board and state
        EightPuzzle board = new EightPuzzle();

        board.setBoard(new char[][]{{'3', '2', '6'}, {'5', '4', '1'}, {'b', '7', '8'}});


        //assert that the board is in the correct state
        assertEquals("3 2 6 \n5 4 1 \nb 7 8 \n", board.toString());

        //extract neighbors of curr state
        List<State> neighbors = board.state().neighbors();


        State secondNeighbor = neighbors.get(1);


        assertEquals("3 2 6 \n5 4 1 \n7 b 8 \n", secondNeighbor.board().toString());

        //print out depth of second neighbor

        assertEquals(1, secondNeighbor.depth());

        //now with this state, get its neighbors
        List<State> secondNeighbors = secondNeighbor.neighbors();

        State thirdNeighbor = secondNeighbors.get(2);


        assertEquals("3 2 6 \n5 4 1 \n7 8 b \n", thirdNeighbor.board().toString());


        assertEquals(2, thirdNeighbor.depth());


    }


    @Test
    public void testHeuristicCost() {

        //test 1
        EightPuzzle puzzle = new EightPuzzle();

        puzzle.setBoard(new char[][]{
                {'7', '2', '4'},
                {'5', 'b', '6'},
                {'8', '3', '1'}});

        State state = puzzle.state();

        int heuristicCost1 = puzzle.h1(state);

        assertEquals(8, heuristicCost1);


        int heuristicCost2 = puzzle.h2(state);

        assertEquals(18, heuristicCost2);


        //test 2 eight puzzle
        EightPuzzle ep2 = new EightPuzzle();

        ep2.setBoard(new char[][]{
                {'2', '8', '3'},
                {'1', '6', '4'},
                {'7', 'b', '5'}});

        State state2 = ep2.state();

        int ep2h1 = ep2.h1(state2);

        assertEquals(8, ep2h1);

        int ep2h2 = ep2.h2(state2);

        assertEquals(15, ep2h2);

        //test 3 eight puzzle
        EightPuzzle ep3 = new EightPuzzle();

        ep3.setBoard(new char[][]{
                {'4', '1', '3'},
                {'b', '2', '5'},
                {'7', '8', '6'}});

        State ep3State = ep3.state();

        int ep3h1 = ep3.h1(ep3State);

        assertEquals(6, ep3h1);

        int ep3h2 = ep3.h2(ep3State);

        assertEquals(11, ep3h2);

        //test 4 eight puzzle
        EightPuzzle ep4 = new EightPuzzle();

        //goal board
        ep4.setBoard(new char[][]{
                {'b', '1', '2'},
                {'3', '4', '5'},
                {'6', '7', '8'}});

        State ep4State = ep4.state();

        int ep4h1 = ep4.h1(ep4State);

        assertEquals(0, ep4h1);

        int ep4h2 = ep4.h2(ep4State);

        assertEquals(0, ep4h2);

        //test 5 eight puzzle
        EightPuzzle ep5 = new EightPuzzle();

        ep5.setBoard(new char[][]{
                {'1', '2', '3'}, // 1 + 1 + 3 + 1 + 1 + 3 + 1 + 1 = 12
                {'4', '5', '6'},
                {'7', '8', 'b'}});

        State ep5State = ep5.state();

        int ep5h1 = ep5.h1(ep5State);

        assertEquals(8, ep5h1);

        int ep5h2 = ep5.h2(ep5State);

        assertEquals(12, ep5h2);

        //looks like h1 and h2 are working correctly


    }


    @Test
    public void testAStar() {

        //very simple case where it moves only one tile
        EightPuzzle eightPuzzle = new EightPuzzle();
        eightPuzzle.setBoard(new char[][]{
                {'3', '1', '2'},
                {'b', '4', '5'},
                {'6', '7', '8'}});
        //  eightPuzzle.solveAStar("h1");

        // test when it's at goal state
        EightPuzzle puzzle2 = new EightPuzzle();


        puzzle2.setBoard(new char[][]{
                {'b', '1', '2'},
                {'3', '4', '5'},
                {'6', '7', '8'}});


        puzzle2.maxNodes(10000);
        //  puzzle2.solveAStar("h1");

        //test 3 A* for h1
        EightPuzzle puzzle3 = new EightPuzzle();

        puzzle3.setBoard(new char[][]{
                {'1', '2', '3'},
                {'4', 'b', '5'},
                {'6', '7', '8'}});

        // puzzle3.solveAStar("h1");

        //test 4 A* for h2
        EightPuzzle puzzle4 = new EightPuzzle();

        puzzle4.setBoard(new char[][]{
                {'b', '2', '3'},
                {'7', '5', '1'},
                {'4', '6', '8'}});


        //“b23751468”

        //puzzle4.maxNodes(10000);


        //puzzle4.solveAStar("h1");


        //3254687b1
        EightPuzzle puzzle5 = new EightPuzzle();


        puzzle5.setBoard(new char[][]{
                {'3', '2', '5'},
                {'4', '6', '8'},
                {'7', 'b', '1'}

        });


        puzzle5.solveAStar("h2");
    }

    @Test
    public void testBeamSearch() {

        EightPuzzle puzzle = new EightPuzzle();

        //test 1: on goal state board
        puzzle.setBoard(new char[][]{
                {'b', '1', '2'},
                {'3', '4', '5'},
                {'6', '7', '8'}});

        //puzzle.solveBeam(3);

        //test 2: on board that requires 1 move
        EightPuzzle puzzle2 = new EightPuzzle();

        puzzle2.setBoard(new char[][]{
                {'3', '1', '2'},
                {'b', '4', '5'},
                {'6', '7', '8'}});

        // puzzle2.solveBeam(5);


        //test 3: on board that requires 2 moves
        EightPuzzle puzzle3 = new EightPuzzle();

        puzzle3.setBoard(new char[][]{
                {'3', '1', '2'},
                {'4', 'b', '5'},
                {'6', '7', '8'}});

        // puzzle3.solveBeam(5);

        //test 4: on board that requires more moves but is
        //b34572168
        EightPuzzle puzzle4 = new EightPuzzle();

        puzzle4.setBoard(new char[][]{
                {'b', '3', '4'},
                {'5', '7', '2'},
                {'1', '6', '8'}});


        // puzzle4.maxNodes(10000);

        // puzzle4.solveBeam(5);


        //test 5: solvable board that requires moves
        EightPuzzle puzzle5 = new EightPuzzle();

        puzzle5.randomizeState(20);

        puzzle5.printState();

        puzzle5.solveBeam(5);
    }

    @Test
    public void testTiming() {


        EightPuzzle ep1 = new EightPuzzle();
        ep1.maxNodes(10000);
        ep1.randomizeState(100);

        long startTime = System.nanoTime();
        ep1.solveAStar("h2");
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("Algorithm took " + duration + " milliseconds to run.");


    }




}