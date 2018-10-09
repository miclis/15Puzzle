import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

// Breadth-First Search (bfs).
// Queue is used to approach the breadth-first search tree.

public class BFSSolver extends PuzzleSolver{

    private static BFSSolver instance = new BFSSolver();
    private Set<PuzzleState> stateSet = new HashSet<PuzzleState>(); // HashSet for puzzle states, eliminates duplicates
    private Queue<PuzzleState> stateQueue = new LinkedList<PuzzleState>();  // FIFO Queue for puzzle states

    private BFSSolver() {

    }

    public static BFSSolver getInstance() {
        return instance;
    }

    private void bfs(PuzzleState state) {

        // Clears memory
        stateSet.clear();
        stateQueue.clear();

        // Adds input state
        stateSet.add(state);
        stateQueue.add(state);

        PuzzleState newState;   // new puzzle state for moves

        while (!stateQueue.isEmpty()){

            state = stateQueue.poll();  // takes 1 element & removes it

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
                stateQueue.add(newState);
            }

            // Move down
            newState = PuzzleState.moveDown(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move left
            newState = PuzzleState.moveLeft(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move right
            newState = PuzzleState.moveRight(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateQueue.add(newState);
            }
        }
    }

    public String solve(Puzzle puzzle) {

        long startTime = System.currentTimeMillis();    // starts timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        bfs(state); // starts bfs search

        time = System.currentTimeMillis() - startTime;  // calculates time

        return getSequence();   // returns sequence of moves
    }
}