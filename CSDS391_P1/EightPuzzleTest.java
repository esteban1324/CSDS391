package EightPuzzlePackage;

import static org.junit.jupiter.api.Assertions.*;

class EightPuzzleTest {

    @org.junit.jupiter.api.Test
    void move() {


        // Create a new board
        EightPuzzle board = new EightPuzzle();

        // get the state of the board
        State state = board.state();

        state.printState();

        // move the blank tile up
        board.move(Direction.DOWN);

        System.out.println();

        // print the new state of the board
        state.printState();

        // Test 2: Move DOWN
        board.move(Direction.DOWN);

        System.out.println();

        state.printState();

        board.move(Direction.UP);

        System.out.println();

        state.printState();

        //move it right
        board.move(Direction.RIGHT);

        System.out.println();

        state.printState();









    }

    @org.junit.jupiter.api.Test
    void board() {
    }

    @org.junit.jupiter.api.Test
    void setState() {
    }
}