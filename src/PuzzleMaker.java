import java.util.Scanner;

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
            String search = scanner.next(); // search type
            String state = scanner.next(); // starting state type

            // Checks if correct arguments were given
            if (size < 2 || !(search.equals("bfs") || search.equals("dfs") ||
                    state.equals("goal") || state.equals("custom"))) {
                throw new UnsupportedOperationException();
            }
            // If custom state
            if (state.equals("custom")) {
                int[][] puzzleArray = new int[size][size];
                for(int i = 0; i < size; i++){
                    for(int j = 0; j < size; j++){
                        puzzleArray[i][j] = scanner.nextInt();  // scans and stores next int in the array
                    }
                }
                puzzle.initialize(puzzleArray, size);   // initialize the custom board
            }
            else {
                puzzle.initialize(size);    // initialize goal board (original)
            }
            scanner.close();

            // Prints initial data:
            System.out.println("Initial board:");
            System.out.println(puzzle.toString());
            puzzle.randomize();
            System.out.println("Randomized board:");
            System.out.println(puzzle.toString());

            // Checks which search algorithm was chosen
            if (search.equals("bfs")) {
                PuzzleSolver sol = BFSSolver.getInstance();
                System.out.println(sol.solve(puzzle));  // prints solution sequence
            }
            else if (search.equals("dfs")){
                PuzzleSolver sol = DFSSolver.getInstance();
                System.out.println(sol.solve(puzzle));  // prints solution sequence
            }
            else if (search.equals("ass")){
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
