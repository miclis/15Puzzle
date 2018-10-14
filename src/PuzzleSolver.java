// Abstract class
public abstract class PuzzleSolver {

    // The goal state, when non-null, puzzle is in goal state.
    protected PuzzleState goal;
    protected long time;    // time taken to solve
    protected long memory;  // memory used to solve
    private static final long MEGABYTE = 1024L * 1024L;

    public abstract String solve(Puzzle puzzle, int heuristicID);    // writes string of moves of zero to reach goal from randomized

    public String getSequence() {

        // Current state is goal state; parent state for printing history.
        PuzzleState current = goal, parent;

        StringBuilder builder = new StringBuilder();
        System.out.println("This is the state sequence (reverse order): ");
        System.out.println("Goal State: ");

        // Prints main output
        if (current != null) {
            while (true) {
                // If at top of the tree == initial state
                if (current.getLevel() == 0) {
                    System.out.println("Program reached the goal state of the puzzle!\n");
                }
                else {
                    // Prints current level in tree
                    System.out.println("Level in tree: " + current.getLevel()); // prints current state
                    System.out.println("Move: " + current.getMove());   // prints movement direction

                }
                System.out.println(current.toString()); // prints current state
                parent = current.getPrev(); // sets parent to current state's previous state

                // No parent
                if (parent == null)
                    break;

                builder.append(current.getMove());  // appends move to solution
                current = parent;   // sets current state to parent
            }

            System.out.println("This solving technique took: " + time + " ms"); //  Time taken
            System.out.println("Memory used: " + memory + " B\t\t| (" + bytesToMegabytes(memory) + "MB)");
            System.out.println("Solution length: " + goal.getLevel());
            goal = null;
            // Writes sequence of moves in reverse order (correct one)
            return "This is the sequence of moves from initial state to the goal state: " + builder.reverse().toString();
        } else
            {
            // System ran out of memory...
            return "System ran out of memory!";
        }
    }
    // Function used to change bytes to Megabytes
    private static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }
}
