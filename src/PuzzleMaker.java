import java.util.Scanner;
import java.io.*;

public class PuzzleMaker {

    /**
     * Takes four parameters:
     * 1. Puzzle size  -   number (3, 8, 15, etc.),
     * 2. Search type  -   string (bfs or dfs),
     * 3. Starting state   -   (goal or custom)
     * 4. If custom: numbers of the puzzle from top row to bottom row in left to right order separated by spaces
     * Examples of commands:    8 bfs goal
     *                          8 dfs custom 0 1 2 3 4 5 6 7 8
     *
     * Input state is randomized to solve.
     */
    public static void main(String[] args){

        try (Scanner scanner = new Scanner(System.in)) {
            // Reads arguments/data from console
            Puzzle puzzle = Puzzle.getInstance();
            int size = (int)Math.sqrt(scanner.nextInt() + 1); // converts String to int and makes sure value is correct
            String type = scanner.next(); // search type

            // Checks if correct arguments were given
            if (size < 2 || !(type.equals("bfs") || type.equals("dfs") || type.equals("ass"))) {
                throw new UnsupportedOperationException();
            }

            // Initializing board from a file
            String fileName = "input8_2.txt";
            String text = null;
            try {
                int[][] puzzleArray = new int[size][size];
                Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
                while(sc.hasNextLine()) {
                    for (int i = 0; i < size; i++) {
                        String[] line = sc.nextLine().trim().split(" ");
                        for (int j = 0; j < size; j++) {
                            puzzleArray[i][j] = Integer.parseInt(line[j]);
                        }
                    }
                }
                puzzle.initialize(puzzleArray, size);
                sc.close();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Unable to open file '" + fileName + "'.");
            }
            scanner.close();

            // Prints initial data:
            System.out.println("Initial board:");
            System.out.println(puzzle.toString());
            //puzzle.randomize();   // uncomment to enable randomization from the given input (input will be your goal)
            //System.out.println("Randomized board:");
            //System.out.println(puzzle.toString());

            // Checks which search algorithm was chosen
            if (type.equals("bfs")) {
                PuzzleSolver sol = BFSSolver.getInstance();
                System.out.println(sol.solve(puzzle));  // prints solution sequence
            }
            else if (type.equals("dfs")){
                PuzzleSolver sol = DFSSolver.getInstance();
                System.out.println(sol.solve(puzzle));  // prints solution sequence
            }
            else if (type.equals("ass")){
                PuzzleSolver sol = ASSolver.getInstance();
                System.out.println(sol.solve(puzzle));  // prints solution sequence
            }
            else{
                throw new UnsupportedOperationException();
            }
        }
        catch (UnsupportedOperationException uoe) {
            System.err.println("Wrong arguments!");
            System.err.println("Please type a puzzle size greater than 2, and then"
                    + " proper solving type (bfs or dfs), then hit enter!");
        }
        finally {
            System.exit(1);
        }
    }
}
