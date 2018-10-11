import java.util.Scanner;
import java.io.*;

public class PuzzleMaker {
    /**
     * Takes four parameters:
     * 1. Puzzle size  -   number (3, 8, 15)
     * 2. Search type  -   String (bfs | idfs | ass | bst)
     * 3. Input file name - String (name of the file located in project's dir, without ".txt")
     * Examples of commands:    8 bfs input8_2
     *                          15 ass input15
     *
     * Input state is randomized to solve.
     */
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            // Reads arguments/data from console
            String sizeS = scanner.next(); // converts String to int and makes sure value is correct
            int size;
            String regex = "[138][5]?";
            if (!sizeS.matches(regex)) {
                throw new UnsupportedOperationException();
            } else {
                size = (int) Math.sqrt(Integer.parseInt(sizeS) + 1);
            }
            String type = scanner.next(); // search type
            Puzzle puzzle = Puzzle.getInstance();
            // Checks if correct arguments were given
            if (size < 2 || !(type.equals("bfs") || type.equals("idfs") || type.equals("ass") || type.equals("bst"))) {
                throw new UnsupportedOperationException();

            }
            String fileName = ".\\res\\" + scanner.next() + ".txt";
            // Initializing board from a file
            try {
                int[][] puzzleArray = new int[size][size];
                Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
                while (sc.hasNextLine()) {
                    for (int i = 0; i < size; i++) {
                        String[] line = sc.nextLine().trim().split(" ");
                        for (int j = 0; j < size; j++) {
                            puzzleArray[i][j] = Integer.parseInt(line[j]);
                        }
                    }
                }
                puzzle.initialize(puzzleArray, size);
                sc.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to open file '" + fileName + "'.");
                throw new UnsupportedOperationException();
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
            } else if (type.equals("idfs")) {
                PuzzleSolver sol = IDFSSolver.getInstance();
                System.out.println(sol.solve(puzzle));  // prints solution sequence
            } else if (type.equals("ass")) {
                PuzzleSolver sol = ASSolver.getInstance();
                System.out.println(sol.solve(puzzle));  // prints solution sequence
            } else if (type.equals("bst")) {
                PuzzleSolver sol = BSTSolver.getInstance();
                System.out.println(sol.solve(puzzle));  // prints solution sequence
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException uoe) {
            System.err.println("Wrong arguments!");
            System.err.println("Please type a proper puzzle size, and then"
                    + " proper solving type (bfs or dfs), then hit enter!");
            System.exit(-1);
        } finally {
            System.exit(1);
        }
    }
}
