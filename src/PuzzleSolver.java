// Abstract class
public abstract class PuzzleSolver {

    // The goal state, when non-null, puzzle is in goal state.
    protected PuzzleState goal;
    protected long time;    // time taken to solve

    public abstract String solve(Puzzle puzzle);    // writes string of moves of zero to reach goal from randomized

    public String getSequence() {

        // Current state is goal state; parent state for printing history.
        PuzzleState current = goal, parent;

        StringBuilder builder = new StringBuilder();
        System.out.println("This is the state sequence from solved state to randomized state(reverse order): ");
        System.out.println("Goal State: ");

        // Prints main output
        if (current != null) {
            while (true) {
                // Prints current level in tree
                System.out.println("Level in tree: " + current.getLevel());

                // If at top of the tree == initial randomized state
                if (current.getLevel() == 0) {
                    System.out.println("Program reached the initial, randomized start state of the puzzle: ");
                }
                else {
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
            goal = null;
            // Writes sequence of moves in reverse order (correct one)
            return "This is the sequence of moves from randomized to goal states: " + builder.reverse().toString();
        } else
            {
            // System ran out of memory...
            return "System ran out of memory!";
        }
    }
}
