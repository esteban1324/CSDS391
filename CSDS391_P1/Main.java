package EightPuzzlePackage;

import java.io.*;

public class Main {

    /* This is the main method for the eight puzzle. This will read commands from text file */
    public static void main(String[] args) throws IOException {


        // create a new eight puzzle
        EightPuzzle ep = new EightPuzzle();

        String name = "C:\\Users\\esteb\\OneDrive\\Documents\\GitHub\\CSDS391\\CSDS391_P1\\EightPuzzlePackage\\commands.txt";
        // read the file
        BufferedReader fr = new BufferedReader(new FileReader(new File("testcmds.txt")));

        // create a new command reader
        String read = fr.readLine();

        // while there are still commands to read
        while((read = fr.readLine()) != null){

        //if the command is to set the eight puzzle state to a specific state
            if(read.substring(0,8).equals("setState")){

                String givenState = read.substring(9,18);

                char[][] newstate = new char[3][3];

                int count = 0;

                //create the new state from the given input string
                for(int row = 0; row < 3; row++){
                    for(int col = 0; col < 3; col++){
                        newstate[row][col] = givenState.charAt(count);
                        count++;
                    }
                }

                // read the givenState from the file and set the state of the eight puzzle to that state
                ep.setBoard(newstate);
            }

            //command to move the blank space any direction
            if(read.startsWith("move")){

                // read the direction from the file and move the blank space in that direction
                ep.move(Direction.valueOf(read.substring(4,7)));

            }

            if(read.startsWith("randomizeState")){
                ep.randomizeState(read.charAt(16));
                // randomize the eight puzzle state with nMoves
            }

            if(read.startsWith("printState")){
              ep.printState();
              // print the eight puzzle state
            }

            if(read.startsWith("solveAStar")){
              //  ep.solveAStar(read.substring(11, read.length()));
                // solve the eight puzzle using A* with the given heuristic
            }

            if(read.startsWith("solveBeam")){
             //   ep.solveBeam(Integer.parseInt(read.substring(11, read.length())));
                // solve the eight puzzle using beam search with the given maxNodes
            }

            if (read.startsWith("maxNodes")){
             //   ep.maxNodes(Integer.parseInt(read.substring(, read.length())));
                // set the maxNodes for beam search
            }
        }
    }
}