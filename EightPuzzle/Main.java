package EightPuzzlePackage;

import java.io.*;
import java.net.URL;

public class Main {
    /* reading in the commands for the file. */
    public static void readFile(String file){
        // create a new eight puzzle
        EightPuzzle ep = new EightPuzzle();

        // create a new command reader
        try (BufferedReader fr = new BufferedReader(new FileReader(file))) {
            // while there are still commands to read
            String read;

            while ((read = fr.readLine()) != null) {

                //if the command is to set the eight puzzle state to a specific state
                if (read.startsWith("setState")) {

                    String givenState = read.substring(9, 18);

                    char[][] newstate = new char[3][3];

                    int count = 0;

                    //create the new state from the given input string
                    for (int row = 0; row < 3; row++) {
                        for (int col = 0; col < 3; col++) {
                            newstate[row][col] = givenState.charAt(count);
                            count++;
                        }
                    }

                    // read the givenState from the file and set the state of the eight puzzle to that state
                    ep.setBoard(newstate);
                }

                //command to move the blank space any direction
                if (read.startsWith("move")) {

                    // read the direction from the file and move the blank space in that direction
                    ep.move(Direction.valueOf(read.substring(4)));

                }

                if (read.startsWith("randomizeState")) {
                    ep.randomizeState(Integer.parseInt(read.substring(15)));
                    // randomize the eight puzzle state with nMoves
                }

                if (read.startsWith("printState")) {
                    ep.printState();
                    // print the eight puzzle state
                }

                if (read.startsWith("solveAStar")) {
                    ep.solveAStar(read.substring(11));
                    // solve the eight puzzle using A* with the given heuristic
                }

                if (read.startsWith("solveBeam")) {
                    ep.solveBeam(Integer.parseInt(read.substring(10)));
                    // solve the eight puzzle using beam search with the given maxNodes
                }

                if (read.startsWith("maxNodes")) {
                    ep.maxNodes(Integer.parseInt(read.substring(9)));
                    // set the maxNodes for beam search
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile2(String file){

        RubixCube r = new RubixCube();
        // create a new command reader
        try (BufferedReader fr = new BufferedReader(new FileReader(file))) {
            // while there are still commands to read
            String read;

            while ((read = fr.readLine()) != null) {

                //if the command is to set the eight puzzle state to a specific state
                if (read.startsWith("setState")) {

                    // read the givenState from the file and set the state of the eight puzzle to that state
                    r.setState(read.substring(9).toCharArray());
                }

                //command to move the blank space any direction
                if (read.startsWith("move")) {

                    // read the direction from the file and move the blank space in that direction
                    r.move(Direction.valueOf(read.substring(4)));

                }

                if (read.startsWith("randomizeState")) {
                    r.randomizeState(Integer.parseInt(read.substring(15)));
                    // randomize the eight puzzle state with nMoves
                }

                if (read.startsWith("printState")) {
                    r.printState();
                    // print the eight puzzle state
                }

                if (read.startsWith("solveAStar")) {
                    r.solveAStar();
                    // solve the eight puzzle using A* with the given heuristic
                }

                if (read.startsWith("solveBeam")) {
                    r.solveBeam(Integer.parseInt(read.substring(10)));
                    // solve the eight puzzle using beam search with the given maxNodes
                }

                if (read.startsWith("maxNodes")) {
                    r.maxNodes(Integer.parseInt(read.substring(9)));
                    // set the maxNodes for beam search
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /* This is the main method for the eight puzzle. This will read commands from text file */
    public static void main(String[] args) {

      /*
        URL path = Main.class.getResource("cmds.txt");
        File f = new File(((URL) path).getFile());
        Main.readFile(f.toString());
       */
        URL path = Main.class.getResource("cmds.txt");
        File f = new File(((URL) path).getFile());
        Main.readFile2(f.toString());

    }
}
