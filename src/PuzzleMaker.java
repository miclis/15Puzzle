import java.util.Scanner;
import java.io.*;
/**
 * Program solves a popular 15 Puzzle Game. It uses various searching algorithms to find the solution.
 * 3 sizes of board are available. Goal patterns are set to:
 * 0    1
 * 2    3
 * for 3 puzzles;
 * 0    1   2
 * 3    4   5
 * 6    7   8
 * for 8 puzzles;
 * 0    1   2   3
 * 4    5   6   7
 * 8    9   10  11
 * 12   13  14  15
 * for 15 puzzles;
 *
 * 6 searching algorithms are available:
 * -    Breadth-First Search
 * -    Depth-First Search
 * -    Recursive Depth-First Search
 * -    Iterative Deepening Depth-First Search
 * -    A*
 * -    Best-First Search
 *
 * The program takes up to four parameters:
 * 1. Puzzle size  -   number (3, 8, 15)
 * 2. Search type  -   String (bfs | dfs | rdfs | idfs | ass | bst)
 * 3. Input file name - String (name of the file located in project's dir, without ".txt")
 * 4. Only for ass & bst! - number (1 or 2)
 *      1   -   Simple heuristic function that calculates how many puzzles are in a wrong position
 *      2   -   Manhattan Distance function, which calculates the value based on how far the tiles are from their
 *              positions
 * Examples of invocation:  8 bfs 8_2
 *                          15 ass 15_1 2
 *                          8  bst 8_1 1
 **/
public class PuzzleMaker {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            // Reads arguments/data from console
            String sizeS = scanner.next(); // converts String to int and makes sure value is correct
            int size;   // size of the row/column
            String regex = "^3|8|15$";  // pattern for the first argument
            if (!sizeS.matches(regex)) {    // checks whether argument is a correct number
                throw new UnsupportedOperationException();
            } else {
                size = (int) Math.sqrt(Integer.parseInt(sizeS) + 1);    // translates number of tiles into row length
            }
            String type = scanner.next(); // search type
            // Checks if correct arguments were given
            if (size < 2 || !(type.equals("bfs") || type.equals("dfs") || type.equals("rdfs") || type.equals("idfs") || type.equals("ass") ||
                    type.equals("bst"))) {
                throw new UnsupportedOperationException();

            }
            String fileName = ".\\res\\" + scanner.next() + ".txt"; // reads name of the input file
            Puzzle puzzle = Puzzle.getInstance();   // creates new instance of puzzle board for storing numbers
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
            // Reads heuristic function ID (for A* and Best-First Search algorithms
            int heuristicID = 0;
            String heuristicIDs;
            if (type.equals("ass") || type.equals("bst"))
            {
                String hRegex = "[12]?";
                heuristicIDs = scanner.next();
                if (!heuristicIDs.matches(hRegex))
                {
                    throw new UnsupportedOperationException();
                }
                else {
                    heuristicID = Integer.parseInt(heuristicIDs);
                }

            }
            scanner.close();    // eliminates the risk of data leaks

            // Prints initial data:
            System.out.println("Initial board:");
            System.out.println(puzzle.toString());
            // Randomization
            //puzzle.randomize();   // uncomment to enable randomization from the given input
            //System.out.println("Randomized board:");
            //System.out.println(puzzle.toString());

            // Checks which search algorithm was chosen
            if (type.equals("bfs")) {
                PuzzleSolver sol = BFSSolver.getInstance();
                System.out.println(sol.solve(puzzle,0));  // solves and prints solution sequence
            } else if (type.equals("dfs")) {
                PuzzleSolver sol = DFSSolver.getInstance();
                System.out.println(sol.solve(puzzle,0));  // solves and prints solution sequence
            } else if (type.equals("rdfs")) {
                PuzzleSolver sol = RDFSSolver.getInstance();
                System.out.println(sol.solve(puzzle,0));  // solves and prints solution sequence
            } else if (type.equals("idfs")) {
                PuzzleSolver sol = IDFSSolver.getInstance();
                System.out.println(sol.solve(puzzle,0));  // solves and prints solution sequence
            } else if (type.equals("ass")) {
                PuzzleSolver sol = ASSolver.getInstance();
                System.out.println(sol.solve(puzzle, heuristicID));  // solves and prints solution sequence
            } else if (type.equals("bst")) {
                PuzzleSolver sol = BSTSolver.getInstance();
                System.out.println(sol.solve(puzzle, heuristicID));  // solves and prints solution sequence
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException uoe) {
            System.err.println("Wrong arguments!");
            System.err.println("Please type a proper puzzle size, and then"
                    + " proper solving type (bfs or dfs), then hit enter!");
            System.exit(-1);    // Program closes with an error
        } finally {
            System.exit(1); // No errors were detected
        }
    }
}
