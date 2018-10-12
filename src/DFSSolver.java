import java.util.HashSet;
import java.util.Set;


// Iterative Deepened Depth-First Search (idfs).
// Queue is used to approach the depth-first search tree.

public class DFSSolver extends PuzzleSolver{

    private static DFSSolver instance = new DFSSolver();
    protected Set<PuzzleState> stateSet = new HashSet<>();

    private DFSSolver(){

    }

    public static DFSSolver getInstance(){
        return instance;
    }

    void dfs(PuzzleState state){

        if(!stateSet.contains(state))
        {
            stateSet.add(state);
        }
        // Save state
        if(state.isGoalState()){
            goal = state;
        }
        PuzzleState newState;   // new puzzle state for moves
        /** CASES */
        // Move up
        newState = PuzzleState.moveUp(state);
        // Checks if exist and not already visited
        Call(newState);

        // Move down
        newState = PuzzleState.moveDown(state);
        // Checks if exist and not already visited
        Call(newState);

        // Move left
        newState = PuzzleState.moveLeft(state);
        // Checks if exist and not already visited
        Call(newState);

        // Move right
        newState = PuzzleState.moveRight(state);
        // Checks if exist and not already visited
        Call(newState);
    }

    void Call(PuzzleState newState){

        // Checks if exist and not already visited
        if(newState != null && !stateSet.contains(newState)){
            stateSet.add(newState);
            dfs(newState);
        }
    }

    public String solve(Puzzle puzzle) {

        long startTime = System.currentTimeMillis();    // starts the timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        dfs(state);
        time = System.currentTimeMillis() - startTime;  // calculate time
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        return getSequence();   // returns sequence of moves
    }
}
