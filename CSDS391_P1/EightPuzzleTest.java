package EightPuzzlePackage;

import static org.junit.jupiter.api.Assertions.*;

class EightPuzzleTest {

    @org.junit.jupiter.api.Test
    void moveFunctionality() {


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

    }
    
    @org.junit.jupiter.api.Test
    void isSolvable(){
        
    }
    
    
    
}