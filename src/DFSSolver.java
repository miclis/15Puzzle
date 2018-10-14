import java.util.HashSet;
import java.util.Stack;
import java.util.Set;

// Iterative Depth-First Search (dfs).
// Queue is used to approach the breadth-first search tree.

public class DFSSolver extends PuzzleSolver{

    private static DFSSolver instance = new DFSSolver();
    private Set<PuzzleState> stateSet = new HashSet<>(); // HashSet for puzzle states, eliminates duplicates
    private Stack<PuzzleState> stateStack = new Stack<>();

    private DFSSolver() {

    }

    public static DFSSolver getInstance() {
        return instance;
    }

    private void dfs(PuzzleState state) {

        // Clears memory
        stateSet.clear();
        stateStack.clear();

        // Adds input state
        stateSet.add(state);
        stateStack.push(state);

        PuzzleState newState;   // new puzzle state for moves

        while (!stateStack.isEmpty()){

            state = stateStack.pop();  // takes 1 element & removes it

            // If goal state
            if (state.isGoalState()) {
                goal = state;
                break;
            }

            // Memory limit check
            if (Runtime.getRuntime().freeMemory() < (.0001) * Runtime.getRuntime().totalMemory()){
                break;
            }

            /** CASES */
            // Move up
            newState = PuzzleState.moveUp(state);
            // Checks if exist and not already visited
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateStack.push(newState);
            }

            // Move down
            newState = PuzzleState.moveDown(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateStack.push(newState);
            }

            // Move left
            newState = PuzzleState.moveLeft(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateStack.push(newState);
            }

            // Move right
            newState = PuzzleState.moveRight(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateStack.push(newState);
            }
        }
    }

    public String solve(Puzzle puzzle) {

        long startTime = System.currentTimeMillis();    // starts timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        dfs(state); // starts bfs search

        time = System.currentTimeMillis() - startTime;  // calculates time
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        return getSequence();   // returns sequence of moves
    }
}